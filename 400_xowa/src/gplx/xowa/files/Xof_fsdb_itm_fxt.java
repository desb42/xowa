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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.files.repos.*;
public class Xof_fsdb_itm_fxt {
	private byte[] wiki_abrv;
	private byte[] lnki_ttl;
	private byte lnki_type;
	private double lnki_upright;
	private int lnki_w;
	private int lnki_h;
	private double lnki_time;
	private int lnki_page;
	private byte orig_repo_id;
	private byte[] orig_repo_name;
	private byte[] orig_ttl;
	private Xof_ext orig_ext;
	private int orig_w;
	private int orig_h;
	private byte[] orig_redirect;
	public Xof_fsdb_itm_fxt() {this.Clear();}
	public void Clear() {
		this.wiki_abrv = lnki_ttl = null;
		this.lnki_type = Xop_lnki_type.Id_null;
		this.lnki_upright = Xof_img_size.Upright_null;
		this.lnki_w = this.lnki_h = this.orig_w = this.orig_h = Xof_img_size.Size__neg1;
		this.lnki_h = Xof_img_size.Size__neg1;
		this.lnki_time = Xof_lnki_time.Null;
		this.lnki_page = Xof_lnki_page.Null;
		this.orig_repo_id = Xof_repo_itm_.Repo_null;
		this.orig_repo_name = orig_ttl = orig_redirect = null;
		this.orig_ext = null;
	}
	public Xof_fsdb_itm_fxt Lnki__en_w(String lnki_ttl_str) {
		this.wiki_abrv = Abrv__en_w;
		this.lnki_ttl = Bry_.new_u8(lnki_ttl_str);
		return this;
	}
	public Xof_fsdb_itm_fxt Orig__commons__lnki() {
		this.orig_repo_name = Xow_domain_itm_.Bry__commons;
		this.orig_repo_id = Xof_repo_itm_.Repo_remote;
		this.orig_ttl = lnki_ttl;
		this.orig_ext = Xof_ext_.new_by_ttl_(orig_ttl);
		this.orig_w = 880;
		this.orig_w = 440;
		return this;
	}
	public Xof_fsdb_itm_fxt Orig__enwiki__lnki() {
		this.orig_repo_name = Xow_domain_itm_.Bry__enwiki;
		this.orig_repo_id = Xof_repo_itm_.Repo_local;
		this.orig_ttl = lnki_ttl;
		this.orig_ext = Xof_ext_.new_by_ttl_(orig_ttl);
		this.orig_w = 880;
		this.orig_w = 440;
		return this;
	}
	public Xof_fsdb_itm Make() {
		Xof_fsdb_itm rv = new Xof_fsdb_itm();
		rv.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, wiki_abrv, lnki_ttl, lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page, Xof_patch_upright_tid_.Tid_all); 
		rv.Init_at_orig(orig_repo_id, orig_repo_name, orig_ttl, orig_ext, orig_w, orig_h, orig_redirect);
		return rv;
	}
	private final static byte[] Abrv__en_w = Bry_.new_a7("en.w");
}
