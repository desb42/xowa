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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.dbs.*;
class Site_interwikimap_tbl implements Db_tbl {
	private static final String tbl_name = "site_interwikimap"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_abrv, fld_prefix, fld_local, fld_language, fld_localinterwiki, fld_url, fld_protorel;
	private final Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_interwikimap_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.Add_str("site_abrv", 255);
		this.fld_prefix					= flds.Add_str("prefix", 255);
		this.fld_local					= flds.Add_bool("local");
		this.fld_language				= flds.Add_str("language", 255);
		this.fld_localinterwiki			= flds.Add_bool("localinterwiki");
		this.fld_url					= flds.Add_str("url", 255);
		this.fld_protorel				= flds.Add_bool("protorel");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, Db_meta_idx.Bld_idx_name(tbl_name, "main"), fld_site_abrv, fld_prefix)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
	}
	public void Select(byte[] site_abrv, Ordered_hash list) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_abrv);
		list.Clear();
		Db_rdr rdr = stmt_select.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Site_interwikimap_itm itm = new Site_interwikimap_itm
				( rdr.Read_bry_by_str(fld_prefix)
				, rdr.Read_bool_by_byte(fld_local)
				, rdr.Read_bry_by_str(fld_language)
				, rdr.Read_bool_by_byte(fld_localinterwiki)
				, rdr.Read_bry_by_str(fld_url)
				, rdr.Read_bool_by_byte(fld_protorel)
				);
				list.Add(itm.Prefix(), itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Insert(byte[] site_abrv, Ordered_hash list) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Site_interwikimap_itm itm = (Site_interwikimap_itm)list.Get_at(i);
			Insert(site_abrv, itm.Prefix(), itm.Local(), itm.Language(), itm.Localinterwiki(), itm.Url(), itm.Protorel());
		}
	}
	private void Insert(byte[] site_abrv, byte[] prefix, boolean local, byte[] language, boolean localinterwiki, byte[] url, boolean protorel) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_site_abrv			, site_abrv)
			.Val_bry_as_str(fld_prefix				, prefix)
			.Val_bool_as_byte(fld_local				, local)
			.Val_bry_as_str(fld_language			, language)
			.Val_bool_as_byte(fld_localinterwiki	, localinterwiki)
			.Val_bry_as_str(fld_url					, url)
			.Val_bool_as_byte(fld_protorel			, protorel)
			.Exec_insert();
	}
}