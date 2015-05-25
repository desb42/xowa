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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Xow_wmf_api_mgr {
	public void Trg_engine_key(String v) {this.trg_engine_key = v;} private String trg_engine_key = gplx.ios.IoEngine_.SysKey;
	public void Api_exec(Xow_wmf_api_wkr wkr) {this.Api_exec(Wikis, wkr);}
	public void Api_exec(String[] wiki_ary, Xow_wmf_api_wkr wkr) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		int len = wiki_ary.length;
		wkr.Api_init();
		for (int i = 0; i < len; ++i) {
			String wiki = wiki_ary[i];
			if (!wkr.Api_wiki_enabled(wiki)) continue;
			String call	= String_.Format("https://{0}/w/api.php?{1}", wiki, wkr.Api_qargs());	// EX: https://en.wikipedia.org/w/api.php?action=query&meta=siteinfo&siprop=namespaces
			usr_dlg.Prog_many("", "", "wmf_api:calling; wiki~{0} api=~{1}", wiki, call);
			byte[] rslt = Io_mgr.I.DownloadFil_args("", null).Trg_engine_key_(trg_engine_key).Exec_as_bry(call);
			if (rslt == null) {usr_dlg.Warn_many("", "", "wmf_api:wmf api returned nothing; api=~{0}", call); continue;}
			wkr.Api_exec(wiki, rslt);
		}
		wkr.Api_term();
	}
	public void Api_exec2(String[] wiki_ary, Xow_wmf_api_wkr[] wkr) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		int len = wiki_ary.length;
		String all_args = "";
		Xowmf_json_tbl json_tbl = new Xowmf_json_tbl(null);
		for (int i = 0; i < len; ++i) {
			String wiki = wiki_ary[i];
			String call	= String_.Format("https://{0}/w/api.php?{1}", wiki, all_args);	// EX: https://en.wikipedia.org/w/api.php?action=query&meta=siteinfo&siprop=namespaces
			usr_dlg.Prog_many("", "", "wmf_api:calling; wiki~{0} api=~{1}", wiki, call);
			byte[] rslt = Io_mgr.I.DownloadFil_args("", null).Trg_engine_key_(trg_engine_key).Exec_as_bry(call);
			if (rslt == null) {usr_dlg.Warn_many("", "", "wmf_api:wmf api returned nothing; api=~{0}", call); continue;}
			int site_id = -1;
			json_tbl.Insert(site_id, DateAdp_.Now(), rslt);
		}
	}
	public static String[] Wikis = new String[]
{ "commons.wikimedia.org"
, "species.wikimedia.org"
, "meta.wikimedia.org"
, "incubator.wikimedia.org"
, "www.wikidata.org"
, "www.mediawiki.org"
, "wikimediafoundation.org"
, "en.wikipedia.org"
, "en.wiktionary.org"
, "en.wikisource.org"
, "en.wikibooks.org"
, "en.wikiversity.org"
, "en.wikiquote.org"
, "en.wikinews.org"
, "en.wikivoyage.org"
, "de.wikipedia.org"
, "de.wiktionary.org"
, "de.wikisource.org"
, "de.wikibooks.org"
, "de.wikiversity.org"
, "de.wikiquote.org"
, "de.wikinews.org"
, "de.wikivoyage.org"
, "es.wikipedia.org"
, "es.wiktionary.org"
, "es.wikisource.org"
, "es.wikibooks.org"
, "es.wikiversity.org"
, "es.wikiquote.org"
, "es.wikinews.org"
, "es.wikivoyage.org"
, "fr.wikipedia.org"
, "fr.wiktionary.org"
, "fr.wikisource.org"
, "fr.wikibooks.org"
, "fr.wikiversity.org"
, "fr.wikiquote.org"
, "fr.wikinews.org"
, "fr.wikivoyage.org"
, "it.wikipedia.org"
, "it.wiktionary.org"
, "it.wikisource.org"
, "it.wikibooks.org"
, "it.wikiversity.org"
, "it.wikiquote.org"
, "it.wikinews.org"
, "it.wikivoyage.org"
, "ja.wikipedia.org"
, "ja.wiktionary.org"
, "ja.wikisource.org"
, "ja.wikibooks.org"
, "ja.wikiversity.org"
, "ja.wikiquote.org"
, "ja.wikinews.org"
, "nl.wikipedia.org"
, "nl.wiktionary.org"
, "nl.wikisource.org"
, "nl.wikibooks.org"
, "nl.wikiquote.org"
, "nl.wikinews.org"
, "nl.wikivoyage.org"
, "nl.wikimedia.org"
, "pl.wikipedia.org"
, "pl.wiktionary.org"
, "pl.wikisource.org"
, "pl.wikibooks.org"
, "pl.wikiquote.org"
, "pl.wikinews.org"
, "pl.wikivoyage.org"
, "pl.wikimedia.org"
, "pt.wikipedia.org"
, "pt.wiktionary.org"
, "pt.wikisource.org"
, "pt.wikibooks.org"
, "pt.wikiversity.org"
, "pt.wikiquote.org"
, "pt.wikinews.org"
, "pt.wikivoyage.org"
, "ru.wikipedia.org"
, "ru.wiktionary.org"
, "ru.wikisource.org"
, "ru.wikibooks.org"
, "ru.wikiversity.org"
, "ru.wikiquote.org"
, "ru.wikinews.org"
, "ru.wikivoyage.org"
, "ru.wikimedia.org"
, "ar.wikipedia.org"
, "ar.wiktionary.org"
, "ar.wikisource.org"
, "ar.wikibooks.org"
, "ar.wikiversity.org"
, "ar.wikiquote.org"
, "ar.wikinews.org"
, "ar.wikimedia.org"
, "ca.wikipedia.org"
, "ca.wiktionary.org"
, "ca.wikisource.org"
, "ca.wikibooks.org"
, "ca.wikiquote.org"
, "ca.wikinews.org"
, "ca.wikimedia.org"
, "cs.wikipedia.org"
, "cs.wiktionary.org"
, "cs.wikisource.org"
, "cs.wikibooks.org"
, "cs.wikiversity.org"
, "cs.wikiquote.org"
, "cs.wikinews.org"
, "da.wikipedia.org"
, "da.wiktionary.org"
, "da.wikisource.org"
, "da.wikibooks.org"
, "da.wikiquote.org"
, "eo.wikipedia.org"
, "eo.wiktionary.org"
, "eo.wikisource.org"
, "eo.wikibooks.org"
, "eo.wikiquote.org"
, "eo.wikinews.org"
, "fa.wikipedia.org"
, "fa.wiktionary.org"
, "fa.wikisource.org"
, "fa.wikibooks.org"
, "fa.wikiquote.org"
, "fa.wikinews.org"
, "fa.wikivoyage.org"
, "fi.wikipedia.org"
, "fi.wiktionary.org"
, "fi.wikisource.org"
, "fi.wikibooks.org"
, "fi.wikiversity.org"
, "fi.wikiquote.org"
, "fi.wikinews.org"
, "fi.wikimedia.org"
, "hu.wikipedia.org"
, "hu.wiktionary.org"
, "hu.wikisource.org"
, "hu.wikibooks.org"
, "hu.wikiquote.org"
, "hu.wikinews.org"
, "id.wikipedia.org"
, "id.wiktionary.org"
, "id.wikisource.org"
, "id.wikibooks.org"
, "id.wikiquote.org"
, "kk.wikipedia.org"
, "kk.wiktionary.org"
, "kk.wikibooks.org"
, "kk.wikiquote.org"
, "ko.wikipedia.org"
, "ko.wiktionary.org"
, "ko.wikisource.org"
, "ko.wikibooks.org"
, "ko.wikiversity.org"
, "ko.wikiquote.org"
, "ko.wikinews.org"
, "lt.wikipedia.org"
, "lt.wiktionary.org"
, "lt.wikisource.org"
, "lt.wikibooks.org"
, "lt.wikiquote.org"
, "no.wikipedia.org"
, "no.wiktionary.org"
, "no.wikisource.org"
, "no.wikibooks.org"
, "no.wikiquote.org"
, "no.wikinews.org"
, "no.wikimedia.org"
, "ro.wikipedia.org"
, "ro.wiktionary.org"
, "ro.wikisource.org"
, "ro.wikibooks.org"
, "ro.wikiquote.org"
, "ro.wikinews.org"
, "ro.wikivoyage.org"
, "sk.wikipedia.org"
, "sk.wiktionary.org"
, "sk.wikisource.org"
, "sk.wikibooks.org"
, "sk.wikiquote.org"
, "sr.wikipedia.org"
, "sr.wiktionary.org"
, "sr.wikisource.org"
, "sr.wikibooks.org"
, "sr.wikiquote.org"
, "sr.wikinews.org"
, "sv.wikipedia.org"
, "sv.wiktionary.org"
, "sv.wikisource.org"
, "sv.wikibooks.org"
, "sv.wikiversity.org"
, "sv.wikiquote.org"
, "sv.wikinews.org"
, "sv.wikivoyage.org"
, "tr.wikipedia.org"
, "tr.wiktionary.org"
, "tr.wikisource.org"
, "tr.wikibooks.org"
, "tr.wikiquote.org"
, "tr.wikinews.org"
, "tr.wikimedia.org"
, "uk.wikipedia.org"
, "uk.wiktionary.org"
, "uk.wikisource.org"
, "uk.wikibooks.org"
, "uk.wikiquote.org"
, "uk.wikinews.org"
, "uk.wikivoyage.org"
, "uk.wikimedia.org"
, "vi.wikipedia.org"
, "vi.wiktionary.org"
, "vi.wikisource.org"
, "vi.wikibooks.org"
, "vi.wikiquote.org"
, "vi.wikivoyage.org"
, "zh.wikipedia.org"
, "zh.wiktionary.org"
, "zh.wikisource.org"
, "zh.wikibooks.org"
, "zh.wikiquote.org"
, "zh.wikinews.org"
, "zh.wikivoyage.org"
, "bg.wikipedia.org"
, "bg.wiktionary.org"
, "bg.wikisource.org"
, "bg.wikibooks.org"
, "bg.wikiquote.org"
, "bg.wikinews.org"
, "el.wikipedia.org"
, "el.wiktionary.org"
, "el.wikisource.org"
, "el.wikibooks.org"
, "el.wikiversity.org"
, "el.wikiquote.org"
, "el.wikinews.org"
, "el.wikivoyage.org"
, "et.wikipedia.org"
, "et.wiktionary.org"
, "et.wikisource.org"
, "et.wikibooks.org"
, "et.wikiquote.org"
, "et.wikimedia.org"
, "eu.wikipedia.org"
, "eu.wiktionary.org"
, "eu.wikibooks.org"
, "eu.wikiquote.org"
, "gl.wikipedia.org"
, "gl.wiktionary.org"
, "gl.wikisource.org"
, "gl.wikibooks.org"
, "gl.wikiquote.org"
, "he.wikipedia.org"
, "he.wiktionary.org"
, "he.wikisource.org"
, "he.wikibooks.org"
, "he.wikiquote.org"
, "he.wikinews.org"
, "he.wikivoyage.org"
, "hr.wikipedia.org"
, "hr.wiktionary.org"
, "hr.wikisource.org"
, "hr.wikibooks.org"
, "hr.wikiquote.org"
, "ms.wikipedia.org"
, "ms.wiktionary.org"
, "ms.wikibooks.org"
, "nn.wikipedia.org"
, "nn.wiktionary.org"
, "nn.wikiquote.org"
, "sh.wikipedia.org"
, "sh.wiktionary.org"
, "simple.wikipedia.org"
, "simple.wiktionary.org"
, "simple.wikibooks.org"
, "simple.wikiquote.org"
, "sl.wikipedia.org"
, "sl.wiktionary.org"
, "sl.wikisource.org"
, "sl.wikibooks.org"
, "sl.wikiversity.org"
, "sl.wikiquote.org"
, "th.wikipedia.org"
, "th.wiktionary.org"
, "th.wikisource.org"
, "th.wikibooks.org"
, "th.wikiquote.org"
, "th.wikinews.org"
, "vo.wikipedia.org"
, "vo.wiktionary.org"
, "vo.wikibooks.org"
, "vo.wikiquote.org"
, "hi.wikipedia.org"
, "hi.wiktionary.org"
, "hi.wikibooks.org"
, "hi.wikiquote.org"
, "ia.wikipedia.org"
, "ia.wiktionary.org"
, "ia.wikibooks.org"
, "la.wikipedia.org"
, "la.wiktionary.org"
, "la.wikisource.org"
, "la.wikibooks.org"
, "la.wikiquote.org"
, "aa.wikipedia.org"
, "aa.wiktionary.org"
, "aa.wikibooks.org"
, "ab.wikipedia.org"
, "ab.wiktionary.org"
, "ace.wikipedia.org"
, "af.wikipedia.org"
, "af.wiktionary.org"
, "af.wikibooks.org"
, "af.wikiquote.org"
, "ak.wikipedia.org"
, "ak.wiktionary.org"
, "ak.wikibooks.org"
, "als.wikipedia.org"
, "als.wiktionary.org"
, "als.wikibooks.org"
, "als.wikiquote.org"
, "am.wikipedia.org"
, "am.wiktionary.org"
, "am.wikiquote.org"
, "an.wikipedia.org"
, "an.wiktionary.org"
, "ang.wikipedia.org"
, "ang.wiktionary.org"
, "ang.wikisource.org"
, "ang.wikibooks.org"
, "ang.wikiquote.org"
, "arc.wikipedia.org"
, "arz.wikipedia.org"
, "as.wikipedia.org"
, "as.wiktionary.org"
, "as.wikisource.org"
, "as.wikibooks.org"
, "ast.wikipedia.org"
, "ast.wiktionary.org"
, "ast.wikibooks.org"
, "ast.wikiquote.org"
, "av.wikipedia.org"
, "av.wiktionary.org"
, "ay.wikipedia.org"
, "ay.wiktionary.org"
, "ay.wikibooks.org"
, "az.wikipedia.org"
, "az.wiktionary.org"
, "az.wikisource.org"
, "az.wikibooks.org"
, "az.wikiquote.org"
, "ba.wikipedia.org"
, "ba.wikibooks.org"
, "bar.wikipedia.org"
, "bat-smg.wikipedia.org"
, "bcl.wikipedia.org"
, "be.wikipedia.org"
, "be.wiktionary.org"
, "be.wikisource.org"
, "be.wikibooks.org"
, "be.wikiquote.org"
, "be.wikimedia.org"
, "be-x-old.wikipedia.org"
, "bh.wikipedia.org"
, "bh.wiktionary.org"
, "bi.wikipedia.org"
, "bi.wiktionary.org"
, "bi.wikibooks.org"
, "bjn.wikipedia.org"
, "bm.wikipedia.org"
, "bm.wiktionary.org"
, "bm.wikibooks.org"
, "bm.wikiquote.org"
, "bn.wikipedia.org"
, "bn.wiktionary.org"
, "bn.wikisource.org"
, "bn.wikibooks.org"
, "bo.wikipedia.org"
, "bo.wiktionary.org"
, "bo.wikibooks.org"
, "bpy.wikipedia.org"
, "br.wikipedia.org"
, "br.wiktionary.org"
, "br.wikisource.org"
, "br.wikiquote.org"
, "br.wikimedia.org"
, "bs.wikipedia.org"
, "bs.wiktionary.org"
, "bs.wikisource.org"
, "bs.wikibooks.org"
, "bs.wikiquote.org"
, "bs.wikinews.org"
, "bug.wikipedia.org"
, "bxr.wikipedia.org"
, "cbk-zam.wikipedia.org"
, "cdo.wikipedia.org"
, "ce.wikipedia.org"
, "ceb.wikipedia.org"
, "ch.wikipedia.org"
, "ch.wiktionary.org"
, "ch.wikibooks.org"
, "cho.wikipedia.org"
, "chr.wikipedia.org"
, "chr.wiktionary.org"
, "chy.wikipedia.org"
, "ckb.wikipedia.org"
, "co.wikipedia.org"
, "co.wiktionary.org"
, "co.wikibooks.org"
, "co.wikiquote.org"
, "co.wikimedia.org"
, "cr.wikipedia.org"
, "cr.wiktionary.org"
, "cr.wikiquote.org"
, "crh.wikipedia.org"
, "csb.wikipedia.org"
, "csb.wiktionary.org"
, "cu.wikipedia.org"
, "cv.wikipedia.org"
, "cv.wikibooks.org"
, "cy.wikipedia.org"
, "cy.wiktionary.org"
, "cy.wikisource.org"
, "cy.wikibooks.org"
, "cy.wikiquote.org"
, "diq.wikipedia.org"
, "dsb.wikipedia.org"
, "dv.wikipedia.org"
, "dv.wiktionary.org"
, "dz.wikipedia.org"
, "dz.wiktionary.org"
, "ee.wikipedia.org"
, "eml.wikipedia.org"
, "ext.wikipedia.org"
, "ff.wikipedia.org"
, "fiu-vro.wikipedia.org"
, "fj.wikipedia.org"
, "fj.wiktionary.org"
, "fo.wikipedia.org"
, "fo.wiktionary.org"
, "fo.wikisource.org"
, "frp.wikipedia.org"
, "frr.wikipedia.org"
, "fur.wikipedia.org"
, "fy.wikipedia.org"
, "fy.wiktionary.org"
, "fy.wikibooks.org"
, "ga.wikipedia.org"
, "ga.wiktionary.org"
, "ga.wikibooks.org"
, "ga.wikiquote.org"
, "gag.wikipedia.org"
, "gan.wikipedia.org"
, "gd.wikipedia.org"
, "gd.wiktionary.org"
, "glk.wikipedia.org"
, "gn.wikipedia.org"
, "gn.wiktionary.org"
, "gn.wikibooks.org"
, "got.wikipedia.org"
, "got.wikibooks.org"
, "gu.wikipedia.org"
, "gu.wiktionary.org"
, "gu.wikisource.org"
, "gu.wikibooks.org"
, "gu.wikiquote.org"
, "gv.wikipedia.org"
, "gv.wiktionary.org"
, "ha.wikipedia.org"
, "ha.wiktionary.org"
, "hak.wikipedia.org"
, "haw.wikipedia.org"
, "hif.wikipedia.org"
, "ho.wikipedia.org"
, "hsb.wikipedia.org"
, "hsb.wiktionary.org"
, "ht.wikipedia.org"
, "ht.wikisource.org"
, "hy.wikipedia.org"
, "hy.wiktionary.org"
, "hy.wikisource.org"
, "hy.wikibooks.org"
, "hy.wikiquote.org"
, "hz.wikipedia.org"
, "ie.wikipedia.org"
, "ie.wiktionary.org"
, "ie.wikibooks.org"
, "ig.wikipedia.org"
, "ii.wikipedia.org"
, "ik.wikipedia.org"
, "ik.wiktionary.org"
, "ilo.wikipedia.org"
, "io.wikipedia.org"
, "io.wiktionary.org"
, "is.wikipedia.org"
, "is.wiktionary.org"
, "is.wikisource.org"
, "is.wikibooks.org"
, "is.wikiquote.org"
, "iu.wikipedia.org"
, "iu.wiktionary.org"
, "jbo.wikipedia.org"
, "jbo.wiktionary.org"
, "jv.wikipedia.org"
, "jv.wiktionary.org"
, "ka.wikipedia.org"
, "ka.wiktionary.org"
, "ka.wikibooks.org"
, "ka.wikiquote.org"
, "kaa.wikipedia.org"
, "kab.wikipedia.org"
, "kbd.wikipedia.org"
, "kg.wikipedia.org"
, "ki.wikipedia.org"
, "kj.wikipedia.org"
, "kl.wikipedia.org"
, "kl.wiktionary.org"
, "km.wikipedia.org"
, "km.wiktionary.org"
, "km.wikibooks.org"
, "kn.wikipedia.org"
, "kn.wiktionary.org"
, "kn.wikisource.org"
, "kn.wikibooks.org"
, "kn.wikiquote.org"
, "koi.wikipedia.org"
, "kr.wikipedia.org"
, "kr.wikiquote.org"
, "krc.wikipedia.org"
, "ks.wikipedia.org"
, "ks.wiktionary.org"
, "ks.wikibooks.org"
, "ks.wikiquote.org"
, "ksh.wikipedia.org"
, "ku.wikipedia.org"
, "ku.wiktionary.org"
, "ku.wikibooks.org"
, "ku.wikiquote.org"
, "kv.wikipedia.org"
, "kw.wikipedia.org"
, "kw.wiktionary.org"
, "kw.wikiquote.org"
, "ky.wikipedia.org"
, "ky.wiktionary.org"
, "ky.wikibooks.org"
, "ky.wikiquote.org"
, "lad.wikipedia.org"
, "lb.wikipedia.org"
, "lb.wiktionary.org"
, "lb.wikibooks.org"
, "lb.wikiquote.org"
, "lbe.wikipedia.org"
, "lez.wikipedia.org"
, "lg.wikipedia.org"
, "li.wikipedia.org"
, "li.wiktionary.org"
, "li.wikisource.org"
, "li.wikibooks.org"
, "li.wikiquote.org"
, "lij.wikipedia.org"
, "lmo.wikipedia.org"
, "ln.wikipedia.org"
, "ln.wiktionary.org"
, "ln.wikibooks.org"
, "lo.wikipedia.org"
, "lo.wiktionary.org"
, "ltg.wikipedia.org"
, "lv.wikipedia.org"
, "lv.wiktionary.org"
, "lv.wikibooks.org"
, "mai.wikipedia.org"
, "map-bms.wikipedia.org"
, "mdf.wikipedia.org"
, "mg.wikipedia.org"
, "mg.wiktionary.org"
, "mg.wikibooks.org"
, "mh.wikipedia.org"
, "mh.wiktionary.org"
, "mhr.wikipedia.org"
, "mi.wikipedia.org"
, "mi.wiktionary.org"
, "mi.wikibooks.org"
, "min.wikipedia.org"
, "mk.wikipedia.org"
, "mk.wiktionary.org"
, "mk.wikisource.org"
, "mk.wikibooks.org"
, "mk.wikimedia.org"
, "ml.wikipedia.org"
, "ml.wiktionary.org"
, "ml.wikisource.org"
, "ml.wikibooks.org"
, "ml.wikiquote.org"
, "mn.wikipedia.org"
, "mn.wiktionary.org"
, "mn.wikibooks.org"
, "mo.wikipedia.org"
, "mo.wiktionary.org"
, "mr.wikipedia.org"
, "mr.wiktionary.org"
, "mr.wikisource.org"
, "mr.wikibooks.org"
, "mr.wikiquote.org"
, "mrj.wikipedia.org"
, "mt.wikipedia.org"
, "mt.wiktionary.org"
, "mus.wikipedia.org"
, "mwl.wikipedia.org"
, "my.wikipedia.org"
, "my.wiktionary.org"
, "my.wikibooks.org"
, "myv.wikipedia.org"
, "mzn.wikipedia.org"
, "na.wikipedia.org"
, "na.wiktionary.org"
, "na.wikibooks.org"
, "na.wikiquote.org"
, "nah.wikipedia.org"
, "nah.wiktionary.org"
, "nah.wikibooks.org"
, "nap.wikipedia.org"
, "nds.wikipedia.org"
, "nds.wiktionary.org"
, "nds.wikibooks.org"
, "nds.wikiquote.org"
, "nds-nl.wikipedia.org"
, "ne.wikipedia.org"
, "ne.wiktionary.org"
, "ne.wikibooks.org"
, "new.wikipedia.org"
, "ng.wikipedia.org"
, "nov.wikipedia.org"
, "nrm.wikipedia.org"
, "nso.wikipedia.org"
, "nv.wikipedia.org"
, "ny.wikipedia.org"
, "oc.wikipedia.org"
, "oc.wiktionary.org"
, "oc.wikibooks.org"
, "om.wikipedia.org"
, "om.wiktionary.org"
, "or.wikipedia.org"
, "or.wiktionary.org"
, "or.wikisource.org"
, "os.wikipedia.org"
, "pa.wikipedia.org"
, "pa.wiktionary.org"
, "pa.wikibooks.org"
, "pag.wikipedia.org"
, "pam.wikipedia.org"
, "pap.wikipedia.org"
, "pcd.wikipedia.org"
, "pdc.wikipedia.org"
, "pfl.wikipedia.org"
, "pi.wikipedia.org"
, "pi.wiktionary.org"
, "pih.wikipedia.org"
, "pms.wikipedia.org"
, "pnb.wikipedia.org"
, "pnb.wiktionary.org"
, "pnt.wikipedia.org"
, "ps.wikipedia.org"
, "ps.wiktionary.org"
, "ps.wikibooks.org"
, "qu.wikipedia.org"
, "qu.wiktionary.org"
, "qu.wikibooks.org"
, "qu.wikiquote.org"
, "rm.wikipedia.org"
, "rm.wiktionary.org"
, "rm.wikibooks.org"
, "rmy.wikipedia.org"
, "rn.wikipedia.org"
, "rn.wiktionary.org"
, "roa-rup.wikipedia.org"
, "roa-rup.wiktionary.org"
, "roa-tara.wikipedia.org"
, "rue.wikipedia.org"
, "rw.wikipedia.org"
, "rw.wiktionary.org"
, "sa.wikipedia.org"
, "sa.wiktionary.org"
, "sa.wikisource.org"
, "sa.wikibooks.org"
, "sa.wikiquote.org"
, "sah.wikipedia.org"
, "sah.wikisource.org"
, "sc.wikipedia.org"
, "sc.wiktionary.org"
, "scn.wikipedia.org"
, "scn.wiktionary.org"
, "sco.wikipedia.org"
, "sd.wikipedia.org"
, "sd.wiktionary.org"
, "sd.wikinews.org"
, "se.wikipedia.org"
, "se.wikibooks.org"
, "se.wikimedia.org"
, "sg.wikipedia.org"
, "sg.wiktionary.org"
, "si.wikipedia.org"
, "si.wiktionary.org"
, "si.wikibooks.org"
, "sm.wikipedia.org"
, "sm.wiktionary.org"
, "sn.wikipedia.org"
, "sn.wiktionary.org"
, "so.wikipedia.org"
, "so.wiktionary.org"
, "sq.wikipedia.org"
, "sq.wiktionary.org"
, "sq.wikibooks.org"
, "sq.wikiquote.org"
, "sq.wikinews.org"
, "srn.wikipedia.org"
, "ss.wikipedia.org"
, "ss.wiktionary.org"
, "st.wikipedia.org"
, "st.wiktionary.org"
, "stq.wikipedia.org"
, "su.wikipedia.org"
, "su.wiktionary.org"
, "su.wikibooks.org"
, "su.wikiquote.org"
, "sw.wikipedia.org"
, "sw.wiktionary.org"
, "sw.wikibooks.org"
, "szl.wikipedia.org"
, "ta.wikipedia.org"
, "ta.wiktionary.org"
, "ta.wikisource.org"
, "ta.wikibooks.org"
, "ta.wikiquote.org"
, "ta.wikinews.org"
, "te.wikipedia.org"
, "te.wiktionary.org"
, "te.wikisource.org"
, "te.wikibooks.org"
, "te.wikiquote.org"
, "tet.wikipedia.org"
, "tg.wikipedia.org"
, "tg.wiktionary.org"
, "tg.wikibooks.org"
, "ti.wikipedia.org"
, "ti.wiktionary.org"
, "tk.wikipedia.org"
, "tk.wiktionary.org"
, "tk.wikibooks.org"
, "tk.wikiquote.org"
, "tl.wikipedia.org"
, "tl.wiktionary.org"
, "tl.wikibooks.org"
, "tn.wikipedia.org"
, "tn.wiktionary.org"
, "to.wikipedia.org"
, "to.wiktionary.org"
, "tpi.wikipedia.org"
, "tpi.wiktionary.org"
, "ts.wikipedia.org"
, "ts.wiktionary.org"
, "tt.wikipedia.org"
, "tt.wiktionary.org"
, "tt.wikibooks.org"
, "tt.wikiquote.org"
, "tum.wikipedia.org"
, "tw.wikipedia.org"
, "tw.wiktionary.org"
, "ty.wikipedia.org"
, "tyv.wikipedia.org"
, "udm.wikipedia.org"
, "ug.wikipedia.org"
, "ug.wiktionary.org"
, "ug.wikibooks.org"
, "ug.wikiquote.org"
, "ur.wikipedia.org"
, "ur.wiktionary.org"
, "ur.wikibooks.org"
, "ur.wikiquote.org"
, "uz.wikipedia.org"
, "uz.wiktionary.org"
, "uz.wikibooks.org"
, "uz.wikiquote.org"
, "ve.wikipedia.org"
, "vec.wikipedia.org"
, "vec.wiktionary.org"
, "vec.wikisource.org"
, "vep.wikipedia.org"
, "vls.wikipedia.org"
, "wa.wikipedia.org"
, "wa.wiktionary.org"
, "wa.wikibooks.org"
, "war.wikipedia.org"
, "wo.wikipedia.org"
, "wo.wiktionary.org"
, "wo.wikiquote.org"
, "wuu.wikipedia.org"
, "xal.wikipedia.org"
, "xh.wikipedia.org"
, "xh.wiktionary.org"
, "xh.wikibooks.org"
, "xmf.wikipedia.org"
, "yi.wikipedia.org"
, "yi.wiktionary.org"
, "yi.wikisource.org"
, "yo.wikipedia.org"
, "yo.wiktionary.org"
, "yo.wikibooks.org"
, "za.wikipedia.org"
, "za.wiktionary.org"
, "za.wikibooks.org"
, "za.wikiquote.org"
, "zea.wikipedia.org"
, "zh-classical.wikipedia.org"
, "zh-min-nan.wikipedia.org"
, "zh-min-nan.wiktionary.org"
, "zh-min-nan.wikisource.org"
, "zh-min-nan.wikibooks.org"
, "zh-min-nan.wikiquote.org"
, "zh-yue.wikipedia.org"
, "zu.wikipedia.org"
, "zu.wiktionary.org"
, "zu.wikibooks.org"
, "ne.wikipedia.org"
, "ne.wiktionary.org"
, "ne.wikibooks.org"
};
//, "als.wikisource.org"
//, "als.wikinews.org"
//, "nds.wikinews.org"
//, "ba.wiktionary.org"
//, "tokipona.wikibooks.org"
//, "ve.wikimedia.org"	// NOTE: moved:DATE:2015-04-06
}
