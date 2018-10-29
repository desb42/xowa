/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.inputBox; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
public class Xtn_inputbox_nde implements Xox_xnde {
	private int mFound, mBgcolor_bgn, mBgcolor_end, mBreak_bgn, mBreak_end, mButtonlabel_bgn, mButtonlabel_end, mDefault_bgn, mDefault_end, mDir_bgn, mDir_end, mEditintro_bgn, mEditintro_end, mFulltextbutton_bgn, mFulltextbutton_end, mHidden_bgn, mHidden_end, mId_bgn, mId_end, mInline_bgn, mInline_end, mLabeltext_bgn, mLabeltext_end, mMinor_bgn, mMinor_end, mNamespaces_bgn, mNamespaces_end, mNosummary_bgn, mNosummary_end, mPage_bgn, mPage_end, mPlaceholder_bgn, mPlaceholder_end, mPrefix_bgn, mPrefix_end, mPreload_bgn, mPreload_end, mSearchbuttonlabel_bgn, mSearchbuttonlabel_end, mSearchfilter_bgn, mSearchfilter_end, mSummary_bgn, mSummary_end, mTour_bgn, mTour_end, mType_bgn, mType_end, mUseve_bgn, mUseve_end, mWidth_bgn, mWidth_end;

	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (itm_bgn == src.length)	return;  // NOTE: handle inline where there is no content to parse; EX: <inputbox/>
		if (itm_bgn >= itm_end)		return;  // NOTE: handle inline where there is no content to parse; EX: a<inputbox/>b
		Parse_lines(src, itm_bgn, itm_end);
		}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (mFound == -1) return;	// inline inputbox; write nothing; EX: <inputbox/>
		render(bfr, src);
		}

	private void Parse_lines(byte[] src, int src_bgn, int src_end) {
		mFound = mBgcolor_bgn = mBgcolor_end = mBreak_bgn = mBreak_end = mButtonlabel_bgn = mButtonlabel_end = mDefault_bgn = mDefault_end = mDir_bgn = mDir_end = mEditintro_bgn = mEditintro_end = mFulltextbutton_bgn = mFulltextbutton_end = mHidden_bgn = mHidden_end = mId_bgn = mId_end = mInline_bgn = mInline_end = mLabeltext_bgn = mLabeltext_end = mMinor_bgn = mMinor_end = mNamespaces_bgn = mNamespaces_end = mNosummary_bgn = mNosummary_end = mPage_bgn = mPage_end = mPlaceholder_bgn = mPlaceholder_end = mPrefix_bgn = mPrefix_end = mPreload_bgn = mPreload_end = mSearchbuttonlabel_bgn = mSearchbuttonlabel_end = mSearchfilter_bgn = mSearchfilter_end = mSummary_bgn = mSummary_end = mTour_bgn = mTour_end = mType_bgn = mType_end = mUseve_bgn = mUseve_end = mWidth_bgn = mWidth_end = -1;
		int line_bgn = src_bgn; boolean line_is_1st = true;
		while (line_bgn < src_end) {																		// iterate over each \n
			int line_end = Bry_find_.Find_fwd(src, Byte_ascii.Nl, line_bgn, src_end);						// find end "\n"
			if (line_end == Bry_find_.Not_found) line_end = src_end;											// no "\n"; use eos;
			int eq_pos = Bry_find_.Find_fwd(src, Byte_ascii.Eq, line_bgn, line_end);
			if (eq_pos != Bry_find_.Not_found) {
				mFound++;
				if (Bry_.Has_at_bgn(src, inputbox_bgcolor, line_bgn, line_end)) {
					mBgcolor_bgn = eq_pos+1;
					mBgcolor_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_break, line_bgn, line_end)) {
					mBreak_bgn = eq_pos+1;
					mBreak_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_buttonlabel, line_bgn, line_end)) {
					mButtonlabel_bgn = eq_pos+1;
					mButtonlabel_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_default, line_bgn, line_end)) {
					mDefault_bgn = eq_pos+1;
					mDefault_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_dir, line_bgn, line_end)) {
					mDir_bgn = eq_pos+1;
					mDir_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_editintro, line_bgn, line_end)) {
					mEditintro_bgn = eq_pos+1;
					mEditintro_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_fulltextbutton, line_bgn, line_end)) {
					mFulltextbutton_bgn = eq_pos+1;
					mFulltextbutton_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_hidden, line_bgn, line_end)) {
					mHidden_bgn = eq_pos+1;
					mHidden_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_id, line_bgn, line_end)) {
					mId_bgn = eq_pos+1;
					mId_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_inline, line_bgn, line_end)) {
					mInline_bgn = eq_pos+1;
					mInline_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_labeltext, line_bgn, line_end)) {
					mLabeltext_bgn = eq_pos+1;
					mLabeltext_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_minor, line_bgn, line_end)) {
					mMinor_bgn = eq_pos+1;
					mMinor_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_namespaces, line_bgn, line_end)) {
					mNamespaces_bgn = eq_pos+1;
					mNamespaces_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_nosummary, line_bgn, line_end)) {
					mNosummary_bgn = eq_pos+1;
					mNosummary_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_page, line_bgn, line_end)) {
					mPage_bgn = eq_pos+1;
					mPage_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_placeholder, line_bgn, line_end)) {
					mPlaceholder_bgn = eq_pos+1;
					mPlaceholder_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_prefix, line_bgn, line_end)) {
					mPrefix_bgn = eq_pos+1;
					mPrefix_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_preload, line_bgn, line_end)) {
					mPreload_bgn = eq_pos+1;
					mPreload_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_searchbuttonlabel, line_bgn, line_end)) {
					mSearchbuttonlabel_bgn = eq_pos+1;
					mSearchbuttonlabel_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_searchfilter, line_bgn, line_end)) {
					mSearchfilter_bgn = eq_pos+1;
					mSearchfilter_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_summary, line_bgn, line_end)) {
					mSummary_bgn = eq_pos+1;
					mSummary_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_tour, line_bgn, line_end)) {
					mTour_bgn = eq_pos+1;
					mTour_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_type, line_bgn, line_end)) {
					mType_bgn = eq_pos+1;
					mType_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_useve, line_bgn, line_end)) {
					mUseve_bgn = eq_pos+1;
					mUseve_end = line_end;
				}
				else if (Bry_.Has_at_bgn(src, inputbox_width, line_bgn, line_end)) {
					mWidth_bgn = eq_pos+1;
					mWidth_end = line_end;
				}
			}
			line_bgn = line_end + 1;																			// +1 to skip over end "\n"
		}
	}

	private void render(Bry_bfr bfr, byte[] src) {
		if (mType_bgn != -1) {
			if (Bry_.Has_at_bgn(src, type_create, mType_bgn, mType_end) ||
					Bry_.Has_at_bgn(src, type_comment, mType_bgn, mType_end)) {
				//$this->mParser->getOutput()->addModules( 'ext.inputBox' );
				getCreateForm(bfr, src);
				return;
			}
			else if (Bry_.Has_at_bgn(src, type_move, mType_bgn, mType_end)) {
				getMoveForm(bfr, src);
				return;
			}
			else if (Bry_.Has_at_bgn(src, type_commenttitle, mType_bgn, mType_end)) {
				getCommentForm(bfr, src);
				return;
			}
			else if (Bry_.Has_at_bgn(src, type_search, mType_bgn, mType_end)) {
				getSearchForm( bfr, src, 0 /*'search'*/ );
				return;
			}
			else if (Bry_.Has_at_bgn(src, type_fulltext, mType_bgn, mType_end)) {
				getSearchForm( bfr, src, 1 /*'fulltext'*/ );
				return;
			}
			else if (Bry_.Has_at_bgn(src, type_search2, mType_bgn, mType_end)) {
				getSearchForm2( bfr, src );
				return;
			}
		}
		bfr.Add(Div_err_bgn);
		if (mType_bgn != -1)
			bfr.Add(inputbox_error_bad_type).Add_mid(src, mType_bgn, mType_end);
		else
			bfr.Add(inputbox_error_no_type);
		bfr.Add(Div_err_end);
	}

	private void getCreateForm(Bry_bfr bfr, byte[] src) {
	}
	private void getMoveForm(Bry_bfr bfr, byte[] src) {
	}
	private void getCommentForm(Bry_bfr bfr, byte[] src) {
	}
	private void getSearchForm(Bry_bfr bfr, byte[] src, int ftype) {
		bfr.Add_bry_many(Div_bgn);
		if (mBgcolor_bgn != -1)
			bfr.Add_mid(src, mBgcolor_bgn, mBgcolor_end);
		else
			bfr.Add(transparent);
		bfr.Add(Form_bgn);
		bfr.Add_bry_many(Inp_bgn); //"<input class=\""
		getLinebreakClasses(bfr);
		bfr.Add(Inp_classes); // "searchboxInput mw-ui-input mw-ui-input-inline\" name=\"search\" type=\""
		if (mHidden_bgn != -1)
			bfr.Add(Inp_hidden);
		else
			bfr.Add(Inp_text);
		bfr.Add(Inp_tween1); // "\" value=\""
		if (mDefault_bgn != -1)
			bfr.Add_mid(src, mDefault_bgn, mDefault_end);
		bfr.Add(Inp_tween2); // "\" placeholder=\""
		if (mPlaceholder_bgn != -1)
			bfr.Add_mid(src, mPlaceholder_bgn, mPlaceholder_end);
		bfr.Add(Inp_tween3); // "\" size=\""
		if (mWidth_bgn != -1)
			bfr.Add_mid(src, mWidth_bgn, mWidth_end);
		bfr.Add(Inp_tween4); // "\" dir=\""
		if (mDir_bgn != -1)
			bfr.Add_mid(src, mDir_bgn, mDir_end);
		else
			bfr.Add(dir_ltr);
		bfr.Add(Inp_end); // "\" />"

		if ( mPrefix_bgn != -1 )
			hidden( bfr, inputbox_prefix, src, mPrefix_bgn, mPrefix_end );

		if ( mSearchfilter_bgn != -1 )
			hidden( bfr, inputbox_searchfilter, src, mSearchfilter_bgn, mSearchfilter_end );

		if ( mTour_bgn != -1 )
			hidden( bfr, inputbox_tour, src, mTour_bgn, mTour_end );

		if (mNamespaces_bgn != -1) {
                    //int a=1;
		}
		else if (ftype == 0) {
			// Go button
			bfr.Add(Go_bgn); //"<input type=\"submit\" name=\"go\" class=\"mw-ui-button\" value=\""
			if ( mButtonlabel_bgn != -1 )
				bfr.Add_mid(src, mButtonlabel_bgn, mButtonlabel_end);
			else
				bfr.Add(inputbox_tryexact);
			bfr.Add(Inp_end); // "\" />"
		}
		// Search button
		bfr.Add(Search_bgn); // "<input type=\"submit\" name=\"fulltext\" class=\"mw-ui-button\" value=\""
		if ( mSearchbuttonlabel_bgn != -1)
			bfr.Add_mid(src, mSearchbuttonlabel_bgn, mSearchbuttonlabel_end);
		else
			bfr.Add(inputbox_searchfulltext);
		bfr.Add(Inp_end); // "\" />"


		// Hidden fulltext param for IE (bug 17161)
		if ( ftype == 1 ) //$type == 'fulltext' )
			hidden( bfr, Fulltext, Search, 0, Search.length );

		bfr.Add(Form_close);
	}

	private void hidden(Bry_bfr bfr, byte[] name, byte[] src, int bgn, int end) {
		bfr.Add(Hid_bgn); // "<input type=\"hidden\" name=\""
		bfr.Add(name);
		bfr.Add(Hid_tween); // "\" value=\""
		bfr.Add_mid(src, bgn, end);
		bfr.Add(Hid_end); // "\" />"
	}
	private void getSearchForm2(Bry_bfr bfr, byte[] src) {
	}
	private void getLinebreakClasses(Bry_bfr bfr) {
	}

	private static byte[]
	  Div_inputbox_bgn = Bry_.new_a7("<div class=\"mw-inputbox-centered\" style=\"background-color: #ffffff;\">")
	, Div_inputbox_end = Bry_.new_a7("\n</div>")
	, Div_err_bgn = Bry_.new_a7("<div><strong class=\"error\">")
	, Div_err_end = Bry_.new_a7("</strong></div>")
	, Inp_bgn = Bry_.new_a7("<input class=\"")
	, Inp_classes = Bry_.new_a7("searchboxInput mw-ui-input mw-ui-input-inline\" name=\"search\" type=\"")
	, Inp_text = Bry_.new_a7("text")
	, Inp_hidden = Bry_.new_a7("hidden")
	, Inp_tween1 = Bry_.new_a7("\" value=\"")
	, Inp_tween2 = Bry_.new_a7("\" placeholder=\"")
	, Inp_tween3 = Bry_.new_a7("\" size=\"")
	, Inp_tween4 = Bry_.new_a7("\" dir=\"")
	, Inp_end = Bry_.new_a7("\" />")
	, dir_ltr = Bry_.new_a7("ltr")
	, Hid_bgn = Bry_.new_a7("<input type=\"hidden\" name=\"")
	, Hid_tween = Bry_.new_a7("\" value=\"")
	, Hid_end = Bry_.new_a7("\" />")
	, Go_bgn = Bry_.new_a7("<input type=\"submit\" name=\"go\" class=\"mw-ui-button\" value=\"")
	, Search_bgn = Bry_.new_a7("<input type=\"submit\" name=\"fulltext\" class=\"mw-ui-button\" value=\"")
	, Search = Bry_.new_a7("Search")
	, Fulltext = Bry_.new_a7("fulltext")
	, inputbox_desc = Bry_.new_a7("Allow inclusion of predefined HTML forms")
	, inputbox_error_no_type = Bry_.new_a7("You have not specified the type of input box to create.")
	, inputbox_error_bad_type = Bry_.new_a7("Input box type \"$1\" not recognized.\nPlease specify \"create\", \"comment\", \"search\", \"search2\" or \"fulltext\".")
	, inputbox_tryexact = Bry_.new_a7("Try exact match")
	, inputbox_searchfulltext = Bry_.new_a7("Search full text")
	, inputbox_createarticle = Bry_.new_a7("Create page")
	, inputbox_movearticle = Bry_.new_a7("Move page")
	, inputbox_postcomment = Bry_.new_a7("New section")
	, inputbox_postcommenttitle = Bry_.new_a7("New section")
	, inputbox_ns_main = Bry_.new_a7("Main")
	, inputbox_bgcolor = Bry_.new_a7("bgcolor")
	, inputbox_break = Bry_.new_a7("break")
	, inputbox_buttonlabel = Bry_.new_a7("buttonlabel")
	, inputbox_default = Bry_.new_a7("default")
	, inputbox_dir = Bry_.new_a7("dir")
	, inputbox_editintro = Bry_.new_a7("editintro")
	, inputbox_fulltextbutton = Bry_.new_a7("fulltextbutton")
	, inputbox_hidden = Bry_.new_a7("hidden")
	, inputbox_id = Bry_.new_a7("id")
	, inputbox_inline = Bry_.new_a7("inline")
	, inputbox_labeltext = Bry_.new_a7("labeltext")
	, inputbox_minor = Bry_.new_a7("minor")
	, inputbox_namespaces = Bry_.new_a7("namespaces")
	, inputbox_nosummary = Bry_.new_a7("nosummary")
	, inputbox_page = Bry_.new_a7("page")
	, inputbox_placeholder = Bry_.new_a7("placeholder")
	, inputbox_prefix = Bry_.new_a7("prefix")
	, inputbox_preload = Bry_.new_a7("preload")
	, inputbox_searchbuttonlabel = Bry_.new_a7("searchbuttonlabel")
	, inputbox_searchfilter = Bry_.new_a7("searchfilter")
	, inputbox_summary = Bry_.new_a7("summary")
	, inputbox_tour = Bry_.new_a7("tour")
	, inputbox_type = Bry_.new_a7("type")
	, inputbox_useve = Bry_.new_a7("useve")
	, inputbox_width = Bry_.new_a7("width")
	, type_create = Bry_.new_a7("create")
	, type_comment = Bry_.new_a7("comment")
	, type_move = Bry_.new_a7("move")
	, type_commenttitle = Bry_.new_a7("commenttitle")
	, type_search = Bry_.new_a7("search")
	, type_search2 = Bry_.new_a7("search2")
	, type_fulltext = Bry_.new_a7("fulltext")
	, Div_bgn = Bry_.new_a7("<div class=\"mw-inputbox-centered\" style=\"background-color: ")
        , Form_bgn = Bry_.new_a7("\">\n<form name=\"searchbox\" class=\"searchbox\" action=\"/wiki/Special:Search\">")
        , Form_close = Bry_.new_a7("</form>\n</div>\n")
	, transparent = Bry_.new_a7("transparent")
	;
	private static final    Bry_fmt fmt = Bry_fmt.Auto_nl_skip_last
	( "<div class=\"mw-inputbox-centered\" style=\"background-color: ~{bgcolor}\">"
	, "  <form name=\"searchbox\" class=\"searchbox\" action=\"/wiki/Special:Search\">"
	, "    <input class=\"~{xtra}searchboxInput mw-ui-input mw-ui-input-inline\" name=\"search\" type=\"~{mhidden}\" value=\"~{mDefaultText}\" placeholder=\"~{mPlaceholderText}\" size=\"~{mWidth}\" dir=\"~{mDir}\" />"
	, "  </form>"
	, "</div>"
	, ""
	);
}
