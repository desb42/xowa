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
package gplx.xowa.wmfs.dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import org.junit.*;
public class Xowm_dump_file_tst {
	private final Xowm_dump_file_fxt fxt = new Xowm_dump_file_fxt();
	@Test  public void Parse()	{fxt.Test_parse("enwiki-20121201-pages-articles.xml.bz2", "en.wikipedia.org", "20121201", Xowm_dump_type_.Int__pages_articles);}
	@Test  public void Bld_dump_dir_url() {
		fxt.Test_bld_dump_dir_url("simplewiki", "latest", "http://dumps.wikimedia.your.org/simplewiki/latest/");
	}
	@Test  public void Bld_dump_file_name() {
		fxt.Test_bld_dump_file_name("simplewiki", "latest", Xowm_dump_type_.Str__pages_articles, "simplewiki-latest-pages-articles.xml.bz2");
	}
}
class Xowm_dump_file_fxt {
	public void Test_parse(String name, String domain, String date, int tid) {
		Xowm_dump_file dump_file = Xowm_dump_file_.parse(Bry_.new_u8(name));
		Tfds.Eq(domain	, dump_file.Domain_itm().Domain_str());
		Tfds.Eq(date	, dump_file.Dump_date());
		Tfds.Eq(tid		, Xowm_dump_type_.parse_by_file(Bry_.new_u8(dump_file.Dump_type_str())));
	}
	public void Test_bld_dump_dir_url(String dump_file, String date, String expd) {
		byte[] actl = Xowm_dump_file_.Bld_dump_dir_url(Bry_.new_a7(Xowm_dump_file_.Server_your_org), Bry_.new_a7(dump_file), Bry_.new_a7(date));
		Tfds.Eq(expd, String_.new_a7(actl));
	}
	public void Test_bld_dump_file_name(String dump_file, String date, String dump_type, String expd) {
		byte[] actl = Xowm_dump_file_.Bld_dump_file_name(Bry_.new_a7(dump_file), Bry_.new_a7(date), Bry_.new_a7(dump_type), Xowm_dump_file_.Ext_xml_bz2);
		Tfds.Eq(expd, String_.new_a7(actl));
	}
}
