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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Mwh_atr_parser_tst {
	private final Mwh_atr_parser_fxt fxt = new Mwh_atr_parser_fxt();
	@Test   public void Pair__quote__double() 			{fxt.Test_parse("a=\"b\""			, fxt.Make_pair("a"		, "b"));}
	@Test   public void Pair__quote__single()			{fxt.Test_parse("a='b'"				, fxt.Make_pair("a"		, "b"));}
	@Test   public void Pair__quote__none()				{fxt.Test_parse("a=b"				, fxt.Make_pair("a"		, "b"));}
	@Test   public void Pair__empty()					{fxt.Test_parse("a=''"				, fxt.Make_pair("a"		, ""));}
	@Test   public void Pair__key_w_underline()			{fxt.Test_parse("a_b=c"				, fxt.Make_pair("a_b"	, "c"));}

	@Test   public void Name__quote__none()				{fxt.Test_parse("b"					, fxt.Make_name("b"));}
	@Test   public void Name__ws()						{fxt.Test_parse(" b "				, fxt.Make_name("b"));}	// PURPOSE:discovered while writing test for ref's "lower-alpha" DATE:2014-07-03
	@Test   public void Name__mult()					{fxt.Test_parse("a b1 c"			, fxt.Make_name("a"), fxt.Make_name("b1"), fxt.Make_name("c"));}

	@Test   public void Fail__key_w_plus() 				{fxt.Test_parse("a+b"				, fxt.Make_fail(0, 3));}
	@Test   public void Fail__key_w_plus__many() 		{fxt.Test_parse("a+b c=d"			, fxt.Make_fail(0, 3)	, fxt.Make_pair("c", "d"));}
	@Test   public void Fail__val_w_plus() 				{fxt.Test_parse("a=b+c"				, fxt.Make_fail(0, 5));}
	@Test   public void Fail__recover() 				{fxt.Test_parse("* a=b"				, fxt.Make_fail(0, 1)	, fxt.Make_pair("a", "b"));}	// PURPOSE: * is invalid, but should not stop parsing of a=b
	@Test   public void Fail__incomplete() 				{fxt.Test_parse("a= c=d"			, fxt.Make_fail(0, 3)	, fxt.Make_pair("c", "d"));}	// PURPOSE: discard xatr if incomplete and followed by valid atr; PAGE:en.w:2013_in_American_television DATE:2014-09-25
	@Test   public void Fail__incomplete_2() 			{fxt.Test_parse("a=c=d"				, fxt.Make_fail(0, 5));}								// PURPOSE: variation of above; per MW regex, missing space invalidates entire attribute; DATE:2014-09-25
	@Test   public void Fail__incomplete_pair()			{fxt.Test_parse("a= b="				, fxt.Make_fail(0, 3)	, fxt.Make_fail(3, 5));}		// PURPOSE: "b=" should be invalid not a kv of "b" = "b"; PAGE:en.s:Notes_by_the_Way/Chapter_2; DATE:2015-01-31

	@Test   public void Dangling_eos() 					{fxt.Test_parse("a='b' c='d"		, fxt.Make_pair("a", "b")	, fxt.Make_fail(5, 10));}	// PURPOSE: handle dangling quote at eos; PAGE:en.w:Aubervilliers DATE:2014-06-25
	@Test   public void Dangling_bos() 					{fxt.Test_parse("a='b c=d"			, fxt.Make_fail(0, 4)		, fxt.Make_pair("c", "d"));}// PURPOSE: handle dangling quote at bos; resume at next valid atr; PAGE:en.w:Aubervilliers DATE:2014-06-25

	@Test   public void Ws__ini()						{fxt.Test_parse(" a='b'"			, fxt.Make_pair("a", "b").Atr_rng(0, 6));}
	@Test   public void Ws__end()						{fxt.Test_parse(" a='b' c='d'"		, fxt.Make_pair("a", "b").Atr_rng(0, 6), fxt.Make_pair("c", "d").Atr_rng(6, 12));}
	@Test   public void Ws()							{fxt.Test_parse("a  = 'b'"			, fxt.Make_pair("a", "b"));}						// PURPOSE: fix wherein multiple space was causing "a=a"; PAGE:fr.s:La_Sculpture_dans_les_cimetières_de_Paris/Père-Lachaise; DATE:2014-01-18

	@Test   public void Many__quote__apos()				{fxt.Test_parse("a='b' c='d' e='f'"	, fxt.Make_pair("a", "b"), fxt.Make_pair("c", "d"), fxt.Make_pair("e", "f"));}
	@Test   public void Many__naked()					{fxt.Test_parse("a=b c=d e=f"		, fxt.Make_pair("a", "b"), fxt.Make_pair("c", "d"), fxt.Make_pair("e", "f"));}

	@Test   public void Val__ws__nl()					{fxt.Test_parse("a='b\nc'"			, fxt.Make_pair("a", "b c"));}
	@Test   public void Val__ws__mult()					{fxt.Test_parse("a='b  c'"			, fxt.Make_pair("a", "b c"));}
	@Test   public void Val__ws__mult_mult()			{fxt.Test_parse("a='b  c d'"		, fxt.Make_pair("a", "b c d"));}	// PURPOSE: fix wherein 1st-gobble gobbled rest of spaces (was b cd)
	@Test   public void Val__apos()						{fxt.Test_parse("a=\"b c'd\""		, fxt.Make_pair("a", "b c'd"));}	// PURPOSE: fix wherein apos was gobbled up; PAGE:en.s:Alice's_Adventures_in_Wonderland; DATE:2013-11-22
	@Test   public void Val__apos_2()					{fxt.Test_parse("a=\"b'c d\""		, fxt.Make_pair("a", "b'c d"));}	// PURPOSE: fix wherein apos was causing "'b'c d"; PAGE:en.s:Grimm's_Household_Tales,_Volume_1; DATE:2013-12-22

	@Test   public void Nowiki__val() 					{fxt.Test_parse("a=<nowiki>'b'</nowiki>"	, fxt.Make_pair("a", "b").Atr_rng(0, 13));}
	@Test   public void Nowiki__key()					{fxt.Test_parse("<nowiki>a=b</nowiki>"		, fxt.Make_pair("a", "b").Atr_rng(8, 11));}
	@Test   public void Nowiki__key_2()					{fxt.Test_parse("a<nowiki>b</nowiki>c=d"	, fxt.Make_pair("abc", "d").Atr_rng(0, 22));}
	@Test   public void Nowiki__key_3()					{fxt.Test_parse("a<nowiki>=</nowiki>\"b\""	, fxt.Make_pair("a", "b").Atr_rng(0, 22));}	// EX:fr.w:{{Portail|Transpédia|Californie}}
	@Test   public void Nowiki__quote()					{fxt.Test_parse("a=\"b<nowiki>c</nowiki>d<nowiki>e</nowiki>f\"", fxt.Make_pair("a", "bcdef"));}

	@Test   public void Val__as_int() 					{fxt.Test_val_as_int("-123"		, -123);}
}