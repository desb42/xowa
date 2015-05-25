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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.criterias.*;
public class Xob_cleanup_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private String bz2_cmd;
	private boolean delete_all, delete_tmp;
	private Criteria_ioMatch[] delete_by_match_ary;
	public Xob_cleanup_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_util_cleanup;}
	public Xob_cleanup_cmd Delete_sqlite3_(boolean v){delete_sqlite3 = v; return this;} private boolean delete_sqlite3;
	public Xob_cleanup_cmd Delete_xml_(boolean v)	{delete_xml = v; return this;} private boolean delete_xml;
	public Xob_cleanup_cmd Delete_tdb_(boolean v)	{delete_tdb = v; return this;} private boolean delete_tdb;
	public void Bz2_fil_(Io_url v)		{bz2_fil = v;} private Io_url bz2_fil;
	public void Cmd_run() {
		Io_url wiki_root_dir = wiki.Fsys_mgr().Root_dir();
		if (bz2_fil != null) {
			if		(String_.Eq(bz2_cmd, "delete"))
				Io_mgr.I.DeleteFil(bz2_fil);
			else if (String_.Eq(bz2_cmd, "move"))
				Io_mgr.I.MoveFil(bz2_fil, bz2_fil.OwnerDir().OwnerDir().GenSubFil_nest("done", bz2_fil.NameAndExt()));
		}
		if (delete_xml)						Io_mgr.I.DeleteFil(Xobd_rdr.Find_fil_by(wiki_root_dir, "*.xml"));
		if (delete_tdb) {
			usr_dlg.Note_many("", "", "bldr.wiki:deleting tdb wiki");
			Delete_tdb(wiki_root_dir);
		}
		if (delete_sqlite3)
			Delete_wiki_sql(wiki);			
		if (delete_all)
			Io_mgr.I.DeleteDir_cmd(wiki_root_dir).Exec();	// do not delete subdirs; needed to support "/prv" for fsdb; DATE:2015-04-01
		if (delete_by_match_ary != null)
			Delete_by_match(wiki_root_dir, delete_by_match_ary);
		if (delete_tmp)
			Io_mgr.I.DeleteDirDeep(wiki_root_dir.GenSubDir("tmp"));
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_bz2_cmd_))				bz2_cmd = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_delete_xml_))			delete_xml = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_delete_wiki_))			delete_tdb = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_delete_sqlite3_))		delete_sqlite3 = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_delete_all_))			delete_all = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_bz2_fil_))				bz2_fil = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_delete_by_match_))		delete_by_match_ary = Delete_by_match_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_delete_tmp_))			delete_tmp = m.ReadYn("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_bz2_cmd_ = "bz2_cmd_", Invk_bz2_fil_ = "bz2_fil_"
	, Invk_delete_xml_ = "delete_xml_", Invk_delete_wiki_ = "delete_wiki_", Invk_delete_sqlite3_ = "delete_sqlite3_"
	, Invk_delete_all_ = "delete_all_"
	, Invk_delete_tmp_ = "delete_tmp_"
	, Invk_delete_by_match_ = "delete_by_match"
	;
	private static Criteria_ioMatch[] Delete_by_match_parse(String raw) {
		String[] match_ary = String_.Split(raw, '|');
		int match_ary_len = match_ary.length;
		Criteria_ioMatch[] rv = new Criteria_ioMatch[match_ary_len];
		for (int i = 0; i < rv.length; i++) {
			String match = match_ary[i];
			rv[i] = Criteria_ioMatch.parse_(true, match, false);
		}
		return rv;
	}
	private static void Delete_by_match(Io_url dir, Criteria_ioMatch[] match_ary) {
		int match_len = match_ary.length;
		Io_url[] subs = Io_mgr.I.QueryDir_fils(dir);
		int subs_len = subs.length;
		for (int i = 0; i < subs_len; i++) {
			Io_url sub = subs[i];
			for (int j = 0; j < match_len; j++) {
				Criteria_ioMatch match = match_ary[j];
				if (match.Matches(sub)) {
					if (sub.Type_fil())
						Io_mgr.I.DeleteFil(sub);
				}
			}
		}
	}
	private static void Delete_tdb(Io_url wiki_root_dir) {
		Io_url[] dirs = Io_mgr.I.QueryDir_args(wiki_root_dir).DirOnly_().DirInclude_().ExecAsUrlAry();
		int dirs_len = dirs.length;
		for (int i = 0; i < dirs_len; i++) {
			Io_url dir = dirs[i];
			if (gplx.xowa.tdbs.Xotdb_dir_info_.Dir_name_is_tdb(dir.NameOnly()))
				Io_mgr.I.DeleteDirDeep(dir);
		}
	}
	public static void Delete_wiki_sql(Xowe_wiki wiki) {
		Gfo_usr_dlg usr_dlg = wiki.Appe().Usr_dlg(); Io_url wiki_root_dir = wiki.Fsys_mgr().Root_dir();
		if (wiki.Db_mgr().Tid() == gplx.xowa.dbs.Xodb_mgr_sql.Tid_sql)		// NOTE: must check; if empty dir (or text db) than db_mgr will be txt
			wiki.Db_mgr_as_sql().Core_data_mgr().Rls();						// NOTE: if sqlite files, must rls;
		Io_url[] files = Io_mgr.I.QueryDir_fils(wiki_root_dir);
		int files_len = files.length;
		int deleted = 0;
		String file_prefix = wiki.Domain_str() + "-file";					// NOTE: skip anything with "-file"; EX: "en.wikipedia.org-file.xowa"
		String html_prefix = wiki.Domain_str() + "-html";					// NOTE: skip anything with "-html"; EX: "en.wikipedia.org-html-ns.000-db.002.xowa"
		for (int i = 0; i < files_len; i++) {
			Io_url url = files[i];
			if (	!String_.Eq(url.Ext(), ".xowa")
				&&	!String_.Eq(url.Ext(), ".sqlite3"))
				continue;
			if (	String_.HasAtBgn(url.NameAndExt(), file_prefix)
				||	String_.HasAtBgn(url.NameAndExt(), html_prefix)
				) continue;	// skip
			Io_mgr.I.DeleteFil(url);
			deleted++;
		}
		usr_dlg.Note_many("", "delete_wiki", "deleting sqlite3 files: ~{0} ~{1}", deleted, wiki_root_dir.Raw());
	}
}
