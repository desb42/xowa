/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.html.hdumps.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.dbs.*; import gplx.ios.*;
import gplx.xowa.dbs.*; import gplx.xowa.pages.*; import gplx.xowa.html.hdumps.core.*; import gplx.xowa.html.hdumps.data.*; import gplx.xowa.html.hdumps.pages.*; import gplx.xowa.pages.skins.*; import gplx.xowa.html.hdumps.data.srl.*;
import gplx.xowa.wikis.data.*; import gplx.xowa2.gui.*;
public class Xohd_page_html_mgr__load {
	private final Xohd_page_srl_mgr srl_mgr = Xohd_page_srl_mgr.I;
	private final Bry_rdr rdr = new Bry_rdr(); private final ListAdp rows = ListAdp_.new_(), imgs = ListAdp_.new_();		
	private static final int redlink_list_max = 1024;
	private final int[] redlink_list = new int[redlink_list_max];
	public void Load_page(Xog_page hpg, Xohd_page_html_tbl tbl, int page_id, Xoa_ttl page_ttl) {
		tbl.Select_by_page(rows, page_id);
		Parse_rows(hpg, page_id, Xoa_url.blank_(), page_ttl, rows);
	}
	public void Parse_rows(Xog_page hpg, int page_id, Xoa_url page_url, Xoa_ttl page_ttl, ListAdp rows) {	// TEST:
		hpg.Init(page_id, page_url, page_ttl);
		imgs.Clear();
		int len = rows.Count();
		for (int i = 0; i < len; ++i) {
			Xohd_page_html_row row = (Xohd_page_html_row)rows.FetchAt(i);
			switch (row.Tid()) {
				case Xohd_page_html_row.Tid_html:			srl_mgr.Load(hpg, row.Data()); break;
				case Xohd_page_html_row.Tid_img:
				case Xohd_page_html_row.Tid_redlink:
															Parse_data(hpg, row); break;
			}
		}
		rows.Clear();
	}
	private void Parse_data(Xog_page hpg, Xohd_page_html_row row) {
		rdr.Src_(row.Data());
		while (!rdr.Pos_is_eos()) {
			int tid = rdr.Read_int_to_pipe();
			switch (tid) {
				case Xohd_data_tid.Tid_img		: Load_data_img(); break;				// 1|0|A.png|0|220|110|...
				case Xohd_data_tid.Tid_gallery	: Load_data_gallery(hpg); break;		// 3|0|800
				case Xohd_data_tid.Tid_redlink	: Load_data_redlink(hpg); break;		// 2|2|0|1
			}
		}
		if (imgs.Count() > 0) hpg.Img_itms_((Xohd_data_itm__base[])imgs.Xto_ary_and_clear(Xohd_data_itm__base.class));
	}
	public static Xohd_data_itm__base Load_img(Bry_rdr rdr) {
		int tid = rdr.Read_int_to_pipe();
		Xohd_data_itm__base img_itm = null;
		switch (tid) {
			case Xohd_data_itm__base.Tid_basic		: img_itm = new Xohd_data_itm__img(); break;
			case Xohd_data_itm__base.Tid_gallery	: img_itm = new Xohd_data_itm__gallery_itm(); break;
		}
		img_itm.Data_parse(rdr);
		rdr.Pos_add_one();
		return img_itm;
	}
	private void Load_data_img() {
		imgs.Add(Load_img(rdr));
	}
	private void Load_data_redlink(Xog_page hpg) {
		int len = 0;
		while (!rdr.Pos_is_eos() && len < redlink_list_max)
			redlink_list[len++] = rdr.Read_int_to_pipe();
		hpg.Redlink_uids_(Int_.Ary_copy(redlink_list, len));
	}
	private void Load_data_gallery(Xog_page hpg) {
		int uid = rdr.Read_int_to_pipe();
		int box_max = rdr.Read_int_to_pipe();
		hpg.Gly_itms().Add(uid, new Xohd_data_itm__gallery_mgr(uid, box_max));
	}
}
