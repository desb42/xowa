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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.divs.*; 
public class Xoh_thm_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final Xoh_img_hzip img_hzip = new Xoh_img_hzip();
	private final Xoh_thm_wtr wtr = new Xoh_thm_wtr();
	private final Bry_obj_ref div_2_capt = Bry_obj_ref.New_empty();
	public int Tid() {return Xoh_hzip_dict_.Tid__thm;}
	public String Key() {return Xoh_hzip_dict_.Key__thm;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_thm_data data = (Xoh_thm_data)data_obj;
		if (!data.Rng_valid()) {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			return this;
		}
		Xoh_thm_caption_data div_2_capt_parser = data.Capt_data();
		int div_1_width = data.Div_1_width();
										  flag_bldr.Set_as_bool(Flag__div_2_capt_moved_by_tidy	, data.Capt_data().Capt_moved_by_tidy());
		boolean div_2_alt_exists			= flag_bldr.Set_as_bool(Flag__div_2_alt_exists			, data.Capt_data().Alt_div_exists());
		boolean div_1_width_exists			= flag_bldr.Set_as_bool(Flag__div_1_width_exists		, div_1_width != 220);
									      flag_bldr.Set_as_byte(Flag__div_0_align				, data.Div_0_align());
		bfr.Add(hook);
		Xoh_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		if (div_1_width_exists) Xoh_hzip_int_.Encode(2, bfr, div_1_width);
		if (div_2_capt_parser.Capt_exists()) bfr.Add_mid(src, div_2_capt_parser.Capt_bgn(), div_2_capt_parser.Capt_end());
		bfr.Add_byte(Xoh_hzip_dict_.Escape);
		if (div_2_alt_exists) bfr.Add_mid(src, div_2_capt_parser.Alt_div_bgn(), div_2_capt_parser.Alt_div_end()).Add_byte(Xoh_hzip_dict_.Escape);
		img_hzip.Encode1(bfr, hdoc_wkr, hctx, hpg, Bool_.N, src, data.Img_data());
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		boolean div_2_capt_moved_by_tidy		= flag_bldr.Get_as_bool(Flag__div_2_capt_moved_by_tidy);
		boolean div_2_alt_exists				= flag_bldr.Get_as_bool(Flag__div_2_alt_exists);
		boolean div_1_width_exists				= flag_bldr.Get_as_bool(Flag__div_1_width_exists);
		int div_0_align						= flag_bldr.Get_as_int(Flag__div_0_align);
		int div_1_width = 220;
		if (div_1_width_exists) div_1_width = rdr.Read_hzip_int(2);
		int capt_bgn = rdr.Pos();
		int capt_end = rdr.Find_fwd_lr();
		div_2_capt.Mid_(src, capt_bgn, capt_end);
		byte[] div_2_alt_bry = div_2_alt_exists ? rdr.Read_bry_to() : Bry_.Empty;

		Xoh_img_data img_data = (Xoh_img_data)hctx.Pool_mgr__data().Get_by_tid(Xoh_hzip_dict_.Tid__img);
		img_hzip.Decode1(bfr, hdoc_wkr, hctx, hpg, rdr, src, rdr.Pos(), src_end, img_data);
		img_hzip.Wtr().Init_by_decode(hpg, hctx, src, img_data);
		wtr.Write(bfr, hpg, hctx, src, img_data.Img_is_vid(), div_0_align, div_1_width, div_2_alt_exists, div_2_alt_bry, img_hzip.Wtr(), img_hzip.Anch_href_bry(), div_2_capt, div_2_capt_moved_by_tidy);
		img_data.Pool__rls();
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_thm_hzip rv = new Xoh_thm_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(1, 1, 1, 3);	
	private static final int // SERIALIZED
	  Flag__div_2_capt_moved_by_tidy	=  0
	, Flag__div_2_alt_exists			=  1
	, Flag__div_1_width_exists			=  2
	, Flag__div_0_align					=  3	// "", "tnone", "tleft", "tcenter", "tright"
	;
}