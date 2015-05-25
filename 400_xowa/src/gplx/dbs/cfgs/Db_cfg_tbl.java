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
package gplx.dbs.cfgs; import gplx.*; import gplx.dbs.*;
import gplx.core.primitives.*;
public class Db_cfg_tbl implements RlsAble {
	private final String tbl_name; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_grp, fld_key, fld_val;
	private Db_stmt stmt_insert, stmt_update, stmt_select;
	public Db_conn Conn() {return conn;} private final Db_conn conn; 
	public Db_cfg_tbl(Db_conn conn, String tbl_name) {
		this.conn = conn; this.tbl_name = tbl_name;
		this.fld_grp				= flds.Add_str("cfg_grp", 255);
		this.fld_key				= flds.Add_str("cfg_key", 255);
		this.fld_val				= flds.Add_str("cfg_val", 1024);
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_update = Db_stmt_.Rls(stmt_update);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_grp, fld_key, fld_val)));}
	public void Delete_val(String grp, String key)	{conn.Stmt_delete(tbl_name, fld_grp, fld_key).Crt_str(fld_grp, grp).Crt_str(fld_key, key).Exec_delete();}
	public void Delete_grp(String grp)				{conn.Stmt_delete(tbl_name, fld_grp).Crt_str(fld_grp, grp).Exec_delete();}
	public void Delete_all()						{conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empy).Exec_delete();}
	public void Insert_yn		(String grp, String key, boolean  val)		{Insert_str(grp, key, val ? "y" : "n");}
	public void Insert_byte		(String grp, String key, byte val)			{Insert_str(grp, key, Byte_.Xto_str(val));}
	public void Insert_int		(String grp, String key, int val)			{Insert_str(grp, key, Int_.Xto_str(val));}
	public void Insert_long		(String grp, String key, long val)			{Insert_str(grp, key, Long_.Xto_str(val));}
	public void Insert_date		(String grp, String key, DateAdp val)		{Insert_str(grp, key, val.XtoStr_fmt_yyyyMMdd_HHmmss());}
	public void Insert_guid		(String grp, String key, Guid_adp val)		{Insert_str(grp, key, val.XtoStr());}
	public void Insert_bry		(String grp, String key, byte[] val)		{Insert_str(grp, key, String_.new_u8(val));}
	public void Insert_str		(String grp, String key, String val) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		try {
			stmt_insert.Clear().Val_str(fld_grp, grp).Val_str(fld_key, key).Val_str(fld_val, val).Exec_insert();
		} catch (Exception e) {throw Err_.new_("db_cfg.insert failed: grp={0} key={1} val={2} db={3} err={4}", grp, key, val, conn.Conn_info().Xto_api(), Err_.Message_lang(e));}
	}
	public void Update_yn		(String grp, String key, boolean  val)		{Update_str(grp, key, val ? "y" : "n");}
	public void Update_byte		(String grp, String key, byte val)			{Update_str(grp, key, Byte_.Xto_str(val));}
	public void Update_int		(String grp, String key, int val)			{Update_str(grp, key, Int_.Xto_str(val));}
	public void Update_long		(String grp, String key, long val)			{Update_str(grp, key, Long_.Xto_str(val));}
	public void Update_date		(String grp, String key, DateAdp val)		{Update_str(grp, key, val.XtoStr_fmt_yyyyMMdd_HHmmss());}
	public void Update_guid		(String grp, String key, Guid_adp val)		{Update_str(grp, key, val.XtoStr());}
	public void Update_bry		(String grp, String key, byte[] val)		{Update_str(grp, key, String_.new_u8(val));}
	public void Update_str		(String grp, String key, String val) {
		if (stmt_update == null) stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_grp, fld_key);
		stmt_update.Clear().Val_str(fld_val, val).Crt_str(fld_grp, grp).Crt_str(fld_key, key).Exec_update();
	}
	public void Upsert_yn		(String grp, String key, boolean val)			{Upsert_str(grp, key, val ? "y" : "n");}
	public void Upsert_int		(String grp, String key, int val)			{Upsert_str(grp, key, Int_.Xto_str(val));}
	public void Upsert_str		(String grp, String key, String val) {
		String cur_val = this.Select_str_or(grp, key, null);
		if (cur_val == null)	this.Insert_str(grp, key, val);
		else					this.Update_str(grp, key, val);
	}
	public boolean			Select_yn		(String grp, String key)				{String val = Select_str(grp, key); return Parse_yn		(grp, key, val);}
	public byte			Select_byte		(String grp, String key)				{String val = Select_str(grp, key); return Parse_byte	(grp, key, val);}
	public int			Select_int		(String grp, String key)				{String val = Select_str(grp, key); return Parse_int	(grp, key, val);}
	public long			Select_long		(String grp, String key)				{String val = Select_str(grp, key); return Parse_long	(grp, key, val);}
	public byte[]		Select_bry		(String grp, String key)				{String val = Select_str(grp, key); return Parse_bry	(grp, key, val);}
	public DateAdp		Select_date		(String grp, String key)				{String val = Select_str(grp, key); return Parse_date	(grp, key, val);}
	public Guid_adp		Select_guid		(String grp, String key)				{String val = Select_str(grp, key); return Parse_guid	(grp, key, val);}
	public boolean			Select_yn_or	(String grp, String key, boolean  or)	{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_yn	(grp, key, val);}
	public byte			Select_byte_or	(String grp, String key, byte or)		{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_byte	(grp, key, val);}
	public int			Select_int_or	(String grp, String key, int or)		{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_int	(grp, key, val);}
	public long			Select_long_or	(String grp, String key, long or)		{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_long	(grp, key, val);}
	public byte[]		Select_bry_or	(String grp, String key, byte[] or)		{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_bry	(grp, key, val);}
	public DateAdp		Select_date_or	(String grp, String key, DateAdp or)	{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_date	(grp, key, val);}
	public Guid_adp		Select_guid_or	(String grp, String key, Guid_adp or)	{String val = Select_str_or(grp, key, null)	; return val == null ? or : Parse_guid	(grp, key, val);}
	public String		Select_str		(String grp, String key) {
		String rv = Select_str_or(grp, key, null); if (rv == null) throw Err_.new_("cfg.missing; grp={0} key={1}", grp, key);
		return rv;
	}
	public String		Select_str_or	(String grp, String key, String or) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, String_.Ary(fld_val), fld_grp, fld_key);
		Db_rdr rdr = stmt_select.Clear().Crt_str(fld_grp, grp).Crt_str(fld_key, key).Exec_select__rls_manual();
		try {return rdr.Move_next() ? rdr.Read_str(fld_val) : or;} finally {rdr.Rls();}
	}
	public Db_cfg_hash Select_as_hash(String grp) {
		Db_cfg_hash rv = new Db_cfg_hash(grp);
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_grp).Crt_str(fld_grp, grp).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				rv.Add(rdr.Read_str(fld_key), rdr.Read_str(fld_val));
			}
		}
		finally {rdr.Rls();}
		return rv;
	}
	// NOTE: Assert guarantees that a value exists in database and returns it (Select + Insert); (1) String val = Assert('grp', 'key', 'val'); (2) Update('grp', 'key', 'val2');
	public boolean			Assert_yn	(String grp, String key, boolean  or)	{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_yn		(grp, key, or); return or;} return Parse_yn		(grp, key, val);}
	public byte			Assert_byte	(String grp, String key, byte or)		{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_byte		(grp, key, or); return or;} return Parse_byte	(grp, key, val);}
	public int			Assert_int	(String grp, String key, int or)		{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_int		(grp, key, or); return or;} return Parse_int	(grp, key, val);}
	public long			Assert_long	(String grp, String key, long or)		{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_long		(grp, key, or); return or;} return Parse_long	(grp, key, val);}
	public byte[]		Assert_bry	(String grp, String key, byte[] or)		{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_bry		(grp, key, or); return or;} return Parse_bry	(grp, key, val);}
	public DateAdp		Assert_date	(String grp, String key, DateAdp or)	{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_date		(grp, key, or); return or;} return Parse_date	(grp, key, val);}
	public Guid_adp		Assert_guid	(String grp, String key, Guid_adp or)	{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_guid		(grp, key, or); return or;} return Parse_guid	(grp, key, val);}
	public String		Assert_str	(String grp, String key, String or)		{String val = Select_str_or(grp, key, null)	; if (val == null) {Insert_str		(grp, key, or); return or;} return val;}
	private boolean		Parse_yn		(String grp, String key, String val)	{try {return Yn.parse_(val)				;} catch (Exception e) {throw err_parse(e, grp, key, val, Bool_.Cls_val_name);}}
	private byte		Parse_byte		(String grp, String key, String val)	{try {return Byte_.parse_(val)			;} catch (Exception e) {throw err_parse(e, grp, key, val, Byte_.Cls_val_name);}}
	private int			Parse_int		(String grp, String key, String val)	{try {return Int_.parse_(val)			;} catch (Exception e) {throw err_parse(e, grp, key, val, Int_.Cls_val_name);}}
	private long		Parse_long		(String grp, String key, String val)	{try {return Long_.parse_(val)			;} catch (Exception e) {throw err_parse(e, grp, key, val, Long_.Cls_val_name);}}
	private byte[]		Parse_bry		(String grp, String key, String val)	{try {return Bry_.new_u8(val)		;} catch (Exception e) {throw err_parse(e, grp, key, val, Bry_.Cls_val_name);}}
	private DateAdp		Parse_date		(String grp, String key, String val)	{try {return DateAdp_.parse_gplx(val)	;} catch (Exception e) {throw err_parse(e, grp, key, val, DateAdp_.Cls_ref_name);}}
	private Guid_adp	Parse_guid		(String grp, String key, String val)	{try {return Guid_adp_.parse_(val)		;} catch (Exception e) {throw err_parse(e, grp, key, val, Guid_adp_.Cls_ref_name);}}
	private Err			err_parse(Exception e, String grp, String key, String val, String type) {return Err_.new_("cfg.val is not parseable; grp={0} key={1} val={2} type={3}", grp, key, val, type);}
}
