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
package gplx.xowa.bldrs.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.core.strings.*;
public class Xob_wiki_cfg_bldr_tst {
	Xob_wiki_cfg_bldr_fxt fxt = new Xob_wiki_cfg_bldr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Exec() {
		fxt	.Init_cmd("en.wikipedia.org", "key0", "en.val0")
			.Init_cmd("en.wikipedia.org", "key1", "en.val1")
			.Init_cmd("fr.wikipedia.org", "key0", "fr.val0")
			.Init_cmd("fr.wikipedia.org", "key1", "fr.val1")
			.Expd_txt("en.wikipedia.org", String_.Concat_lines_nl
			(	"// key0.bgn"
			,	"en.val0"
			,	"// key0.end"
			,	"// key1.bgn"
			,	"en.val1"
			,	"// key1.end"
			))
			.Expd_txt("fr.wikipedia.org", String_.Concat_lines_nl
			(	"// key0.bgn"
			,	"fr.val0"
			,	"// key0.end"
			,	"// key1.bgn"
			,	"fr.val1"
			,	"// key1.end"
			))
			.Test()
			;
		fxt	.Clear()
			.Init_cmd("en.wikipedia.org", "key2", "en.val2")
			.Expd_txt("en.wikipedia.org", String_.Concat_lines_nl
			(	"// key0.bgn"
			,	"en.val0"
			,	"// key0.end"
			,	"// key1.bgn"
			,	"en.val1"
			,	"// key1.end"
			,	"// key2.bgn"
			,	"en.val2"
			,	"// key2.end"
			));
	}
//		@Test  public void Lang_names_run() {
//			Io_url dir = Io_url_.new_dir_("/var/www/mediawiki/languages/messages/");
//			Io_url[] fils = Io_mgr.I.QueryDir_args(dir).ExecAsUrlAry();
//			int fils_len = fils.length;
//			String_bldr sb = String_bldr_.new_();
//			for (int i = 0; i < fils_len; i++) {
//				Io_url fil = fils[i];
//				String lang_code = String_.Lower(String_.Replace(fil.NameOnly(), "Messages", ""));
//				String txt = Io_mgr.I.LoadFilStr(fil);
//				String[] lines = String_.Split(txt, '\n');
//				String line = lines[1];
//				line = String_.Replace(line, "/** ", "");
//				int pos = String_.FindBwd(line, " (");
//				if (pos == String_.Find_none) continue;	// en; en_rtl have no "language" line
//				if (	String_.HasAtBgn(lang_code, "be_")
//					|| 	String_.HasAtBgn(lang_code, "crh_")
//					|| 	String_.HasAtBgn(lang_code, "kk_")
//					|| 	String_.HasAtBgn(lang_code, "ku_")
//					|| 	String_.HasAtBgn(lang_code, "sr_")
//					|| 	String_.In(lang_code, "de_formal", "nb", "nl_informal", "nn", "no")
//					) {
//					int new_pos = String_.FindBwd(line, " (", pos - 1);
//					if (new_pos != String_.Find_none)
//						pos = new_pos;
//				}
//				line = Replace_by_pos(line, pos, pos + 2, "|");
//				int line_len = String_.Len(line);
//				if (line_len > 0)
//					line = String_.MidByLen(line, 0, line_len - 1);
//				String[] terms = String_.Split(line, '|');
//				sb.Add(lang_code).Add("|").Add(String_.Trim(terms[0])).Add("|").Add(String_.Trim(terms[1])).Add("\n");
//			}
//			Tfds.Write(sb.Xto_str_and_clear());
//		}
	@Test  public void Ns_aliases() {
		Io_mgr.I.InitEngine_mem();
		Io_mgr.I.SaveFilStr("mem/en.wikipedia.org/w/api.php?action=query&format=xml&meta=siteinfo&siprop=namespacealiases", String_.Concat_lines_nl
		(	"<api>"
		,	"<query>"
		,	"<namespacealiases>"
		,	"<ns id=\"4\" xml:space=\"preserve\">WP"
		,	"</ns>"
		,	"<ns id=\"5\" xml:space=\"preserve\">WT"
		,	"</ns>"
		,	"<ns id=\"6\" xml:space=\"preserve\">Image"
		,	"</ns>"
		,	"<ns id=\"7\" xml:space=\"preserve\">Image talk"
		,	"</ns>"
		,	"</namespacealiases>"
		,	"</query>"
		,	"</api>"
		));
		String[] wikis = new String[] {"en.wikipedia.org"}; String protocol = "mem/";
		Tfds.Eq_str_lines(Query_ns(protocol, gplx.ios.IoEngine_.MemKey, wikis), String_.Concat_lines_nl
		(	"app.bldr.wiki_cfg_bldr.get('en.wikipedia.org').new_cmd_('wiki.ns_mgr.aliases', 'ns_mgr.add_alias_bulk(\""
		,	"4|WP"
		,	"5|WT"
		,	"6|Image"
		,	"7|Image_talk"
		,	"\");');"
		));
	}
	String Query_ns(String protocol, String trg_engine_key, String[] wikis) {
		String_bldr sb = String_bldr_.new_();
		int wikis_len = wikis.length;
		for (int i = 0; i < wikis_len; i++) {
			String wiki = wikis[i];
			if (String_.Len_eq_0(wiki)) continue;
			try {
			String api = protocol + wiki + "/w/api.php?action=query&format=xml&meta=siteinfo&siprop=namespacealiases";
			String xml = String_.new_u8(Io_mgr.I.DownloadFil_args("", null).Trg_engine_key_(trg_engine_key).Exec_as_bry(api));
			if (xml == null) continue;	// not found
			gplx.xmls.XmlDoc xdoc = gplx.xmls.XmlDoc_.parse_(xml);
			gplx.xmls.XmlNde xnde = gplx.xmls.Xpath_.SelectFirst(xdoc.Root(), "query/namespacealiases");
			sb.Add("app.bldr.wiki_cfg_bldr.get('").Add(wiki).Add("').new_cmd_('wiki.ns_mgr.aliases', 'ns_mgr.add_alias_bulk(\"\n");
			int xndes_len = xnde.SubNdes().Count();
			for (int j = 0; j < xndes_len; j++) {
				gplx.xmls.XmlNde ns_nde = xnde.SubNdes().Get_at(j);
				if (!String_.Eq(ns_nde.Name(), "ns")) continue;
				int id = Int_.parse_(ns_nde.Atrs().FetchValOr("id", "-1"));
				String name = String_.Replace(String_.Replace(ns_nde.Text_inner(), " ", "_"), "'", "''");
				sb.Add(Int_.Xto_str(id)).Add("|").Add(String_.Trim(name)).Add_char_nl();
			}
			sb.Add("\");');\n");
			}
			catch(Exception e) {sb.Add("// fail: " + wiki + " " + Err_.Message_gplx_brief(e)).Add_char_nl();}
		}
		return sb.Xto_str_and_clear();
	}
}
class Xob_wiki_cfg_bldr_fxt {
	public Xob_wiki_cfg_bldr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki_cfg_bldr = app.Bldr().Wiki_cfg_bldr();
		}
		wiki_cfg_bldr.Clear();
		hash.Clear();
		return this;
	}	private Xoae_app app; Xob_wiki_cfg_bldr wiki_cfg_bldr; Ordered_hash hash = Ordered_hash_.new_();
	public Xob_wiki_cfg_bldr_fxt Init_cmd(String wiki, String key, String text) {
		wiki_cfg_bldr.Itms_get_or_new(wiki).Itms_add(key, text);
		return this;
	}
	public Xob_wiki_cfg_bldr_fxt Expd_txt(String wiki, String text) {
		hash.Add(wiki, KeyVal_.new_(wiki, text));
		return this;
	}
	public void Test() {
		wiki_cfg_bldr.Exec();
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			KeyVal kv = (KeyVal)hash.Get_at(i);
			String wiki = kv.Key();
			String expd = (String)kv.Val();
			String actl = Io_mgr.I.LoadFilStr(app.Fsys_mgr().Cfg_wiki_core_dir().GenSubFil(wiki + ".gfs"));
			Tfds.Eq_str_lines(expd, actl);
		}
	}
}
