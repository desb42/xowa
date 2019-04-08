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
package gplx.xowa.htmls;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.OutputStream; import java.io.FileOutputStream; import java.io.File; import java.io.IOException;
import java.io.BufferedReader; import java.io.*;
import java.util.Stack;

public class Xoh_css_minify {
	private Stack<String> preservedTokens;
	private Stack<String> preservedComments;

	/*
	Heavily adapted from yui/ycssmin/cssmin.js
	*/
	private String extractDataUrls(String css) {
	
		// Leave data urls alone to increase parse performance.
		int maxIndex = css.length() - 1,
			appendIndex = 0,
			startIndex,
			endIndex;
		String terminator,
			preserver,
			token;
		boolean foundTerminator;
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("url\\(\\s*([\"']?)data\\:");
		Matcher m = p.matcher(css);
		boolean found = false;
	
		// Since we need to account for non-base64 data urls, we need to handle
		// ' and ) being part of the data string. Hence switching to indexOf,
		// to determine whether or not we have matching string terminators and
		// handling sb appends directly, instead of using matcher.append* methods.
	
		while (m.find()) {
			found = true;
			startIndex = m.start() + 4;  // "url(".length()
			terminator = m.group(1);	 // ', " or empty (not quoted)
	
			if (terminator.length() == 0) {
				terminator = ")";
			}
	
			foundTerminator = false;
	
			endIndex = m.end() - 1;
	
			while(foundTerminator == false && endIndex+1 <= maxIndex) {
				endIndex = css.indexOf(terminator, endIndex + 1);
	
				// endIndex == 0 doesn't really apply here
				if ((endIndex > 0) && (css.charAt(endIndex - 1) != '\\')) {
					foundTerminator = true;
					if (!")".equals(terminator)) {
						endIndex = css.indexOf(")", endIndex);
					}
				}
			}
	
			// Enough searching, start moving stuff over to the buffer
			sb.append(css.substring(appendIndex, m.start()));
	
			if (foundTerminator) {
				token = css.substring(startIndex, endIndex);
				token = token.replaceAll("\\s+", "");
				preservedTokens.push(token);
	
				preserver = "url(___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___)";
				sb.append(preserver);
	
				appendIndex = endIndex + 1;
			} else {
				// No end terminator found, re-add the whole match. Should we throw/warn here?
				sb.append(css.substring(m.start(), m.end()));
				appendIndex = m.end();
			}
		}
	
		sb.append(css.substring(appendIndex));
	
		if (found)
			return sb.toString();
		else
			return css;
	};
	// preserve strings so their content doesn't get accidentally minified
	private String preservestring(String css) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("(\"([^\\\\\"]|\\\\.|\\\\)*\")|('([^\\\\']|\\\\.|\\\\)*')");
		Matcher m = p.matcher(css);
		
		while (m.find()) {
			System.out.println(m);
			
			String match = m.group();
			String quote = match.substring(0,1);
	
			// maybe the string contains a comment-like substring?
			// one, maybe more? put'em back then
			if (match.indexOf("___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_") >= 0) {
				for (int i = 0, max = preservedComments.size(); i < max; i = i + 1) {
					match = match.replace("___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + i + "___", preservedComments.get(i));
				}
			}
	
			// minify alpha opacity in filter strings
			match = match.replaceAll("(?i)progid:DXImageTransform\\.Microsoft\\.Alpha\\(Opacity=", "alpha(opacity=");
	
			preservedTokens.push(match);
			String str = quote + "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___" + quote;
			m.appendReplacement(sb, str);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	private String preservepseudo(String css) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("(^|\\})(([^\\{:])+:)+([^\\{]*\\{)");
		Matcher m = p.matcher(css);
		
		while (m.find()) {
			String str = m.group();
			str = str.replace(":", "___YUICSSMIN_PSEUDOCLASSCOLON___");
			m.appendReplacement(sb, str);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	private String backgroundpos(String css) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("(background-position|transform-origin|webkit-transform-origin|moz-transform-origin|o-transform-origin|ms-transform-origin):0(;|\\})", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(css);
		
		while (m.find()) {
			String prop = m.group(1), tail = m.group(2);
			m.appendReplacement(sb, prop.toLowerCase() + ":0 0" + tail);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	private String rgbcvt(String css) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("rgb\\s*\\(\\s*([0-9,\\s]+)\\s*\\)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(css);
		
		while (m.find()) {
			String[] rgbcolors = m.group(1).split(",");
			String rgb = "";
			for (int i = 0; i < rgbcolors.length; i = i + 1) {
				rgbcolors[i] = Integer.toHexString(Integer.parseInt(rgbcolors[i], 10));
				if (rgbcolors[i].length() == 1) {
					rgbcolors[i] = "0" + rgbcolors[i];
				}
				rgb += rgbcolors[i];
			}
			m.appendReplacement(sb, '#' + rgb);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	private String borders(String css) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("(border|border-top|border-right|border-bottom|border-right|outline|background):none(;|\\})", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(css);
		
		while (m.find()) {
			String prop = m.group(1), tail = m.group(2);
			m.appendReplacement(sb, prop.toLowerCase() + ":0" + tail);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public String cssmin(String css, int linebreakpos) {
	
		int startIndex = 0,
			endIndex = 0,
			i = 0, max = 0,
			totallen = css.length();
			//preservedTokens = [],
			//comments = [],
		String token = "",
			placeholder = "";
	
		preservedTokens = new Stack<>();
		preservedComments = new Stack<>();
		css = extractDataUrls(css);
	
		// collect all comment blocks...
		while ((startIndex = css.indexOf("/*", startIndex)) >= 0) {
			endIndex = css.indexOf("*/", startIndex + 2);
			if (endIndex < 0) {
				endIndex = totallen;
			}
			token = css.substring(startIndex + 2, endIndex);
			preservedComments.push(token);
			css = css.substring(0, startIndex + 2) + "___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + (preservedComments.size() - 1) + "___" + css.substring(endIndex);
			startIndex += 2;
		}
		
		css = preservestring(css);
		
		// strings are safe, now wrestle the comments
		for (i = 0, max = preservedComments.size(); i < max; i = i + 1) {
	
			token = preservedComments.get(i);
			placeholder = "___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + i + "___";
	
			// ! in the first position of the comment means preserve
			// so push to the preserved tokens keeping the !
			if (token.charAt(0) == '!') {
				preservedTokens.push(token);
				css = css.replace(placeholder,  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
				continue;
			}
	
			// \ in the last position looks like hack for Mac/IE5
			// shorten that to /*\*/ and the next one to /**/
			if (token.charAt(token.length() - 1) == '\\') {
				preservedTokens.push("\\");
				css = css.replace(placeholder,  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
				i = i + 1; // attn: advancing the loop
				preservedTokens.push("");
				css = css.replace("___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + i + "___",
					"___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
				continue;
			}
	
			// keep empty comments after child selectors (IE7 hack)
			// e.g. html >/**/ body
			if (token.length() == 0) {
				startIndex = css.indexOf(placeholder);
				if (startIndex > 2) {
					if (css.charAt(startIndex - 3) == '>') {
						preservedTokens.push("");
						css = css.replace(placeholder,  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
					}
				}
			}
	
			// in all other cases kill the comment
			css = css.replace("/*" + placeholder + "*/", "");
		}
	
	
		// Normalize all whitespace strings to single spaces. Easier to work with that way.
		css = css.replaceAll("\\s+", " ");
	
		// Remove the spaces before the things that should not have spaces before them.
		// But, be careful not to turn "p :link {...}" into "p:link{...}"
		// Swap out any pseudo-class colons with the token, and then swap back.
		css = preservepseudo(css);
	
		css = css.replaceAll("\\s+([!{};:>+\\(\\)\\],])", "$1");
		css = css.replace("___YUICSSMIN_PSEUDOCLASSCOLON___", ":");
	
		// retain space for special IE6 cases
		css = css.replaceAll(":first-(line|letter)(\\{|,)", ":first-$1 $2");
	
		// no space after the end of a preserved comment
		css = css.replace("*/ ", "*/");
	
	
		// If there is a @charset, then only allow one, and push to the top of the file.
		css = css.replaceAll("(?i)^(.*)(@charset \"[^\"]*\";)", "$2$1");
		css = css.replaceAll("(?i)^(\\s*@charset [^;]+;\\s*)+", "$1");
	
		// Put the space back in some cases, to support stuff like
		// @media screen and (-webkit-min-device-pixel-ratio:0){
		css = css.replaceAll("(?i)\\band\\(", "and (");
	
	
		// Remove the spaces after the things that should not have spaces after them.
		css = css.replaceAll("([!{}:;>+\\(\\[,])\\s+", "$1");
	
		// remove unnecessary semicolons
		css = css.replaceAll(";+\\}", "}");
	
		// Replace 0(px,em,%) with 0.
		css = css.replaceAll("(?i)([\\s:])(0)(px|em|%|in|cm|mm|pc|pt|ex)", "$1$2");
	
		// Replace 0 0 0 0; with 0.
		css = css.replaceAll(":0 0 0 0(;|\\})", ":0$1");
		css = css.replaceAll(":0 0 0(;|\\})", ":0$1");
		css = css.replaceAll(":0 0(;|\\})", ":0$1");
	
		// Replace background-position:0; with background-position:0 0;
		// same for transform-origin
		css = backgroundpos(css);
	
		// Replace 0.6 to .6, but only when preceded by : or a white-space
		css = css.replaceAll("(:|\\s)0+\\.(\\d+)", "$1.$2");
	
		// Shorten colors from rgb(51,102,153) to #336699
		// This makes it more likely that it'll get further compressed in the next step.
		css = rgbcvt(css);
	
	
		// Shorten colors from #AABBCC to #ABC. Note that we want to make sure
		// the color is not preceded by either ", " or =. Indeed, the property
		//	 filter: chroma(color="#FFFFFF");
		// would become
		//	 filter: chroma(color="#FFF");
		// which makes the filter break in IE.
		/*
		css = css.replace(/([^"'=\s])(\s*)#([0-9a-f])([0-9a-f])([0-9a-f])([0-9a-f])([0-9a-f])([0-9a-f])/gi, function () {
			var group = arguments;
			if (
				group[3].toLowerCase() === group[4].toLowerCase() &&
				group[5].toLowerCase() === group[6].toLowerCase() &&
				group[7].toLowerCase() === group[8].toLowerCase()
			) {
				return (group[1] + group[2] + '#' + group[3] + group[5] + group[7]).toLowerCase();
			} else {
				return group[0].toLowerCase();
			}
		});
		*/
		//css = _compressHexColors(css);
	
		// border: none -> border:0
		css = borders(css);
	
		// shorter opacity IE filter
		css = css.replaceAll("(?i)progid:DXImageTransform\\.Microsoft\\.Alpha\\(Opacity=", "alpha(opacity=");
	
		// Remove empty rules.
		css = css.replaceAll("[^\\};\\{\\/]+\\{\\}", "");
	
		if (linebreakpos >= 0) {
			// Some source control tools don't like it when files containing lines longer
			// than, say 8000 characters, are checked in. The linebreak option is used in
			// that case to split long lines after a specific column.
			startIndex = 0;
			i = 0;
	//		while (i < css.length()) {
	//			i = i + 1;
	//			if (css[i - 1] == '}' && i - startIndex > linebreakpos) {
	//				css = css.slice(0, i) + '\n' + css.slice(i);
	//				startIndex = i;
	//			}
	//		}
		}
	
		// Replace multiple semi-colons in a row by a single one
		// See SF bug #1980989
		css = css.replaceAll(";;+", ";");
	
		// restore preserved comments and strings
		for (i = 0, max = preservedTokens.size(); i < max; i = i + 1) {
			css = css.replace("___YUICSSMIN_PRESERVED_TOKEN_" + i + "___", preservedTokens.get(i));
		}
	
		// Trim the final string (for any leading or trailing white spaces)
		css = css.replaceAll("^\\s+|\\s+$", "");
	
		// add the '.mw-parser-output ' selector
		css = css.replaceAll("(\\}[^@}].{2})", "$1.mw-parser-output ");
		css = css.replaceAll("(@media[^\\{]*\\{)", "$1.mw-parser-output ");
		if (css.charAt(0) != '@')
			css = ".mw-parser-output " + css;
	
		return css;
	}
	public String readFileAsString(String filePath) {
		FileReader fr;
		try {
			fr = new FileReader(filePath);
					} catch (IOException e) {
						e.printStackTrace();
						return "";
					}
			StringBuffer fileData = new StringBuffer();
			BufferedReader reader = new BufferedReader(fr);
			char[] buf = new char[1024];
			int numRead=0;
			while(true) {
				try {
					numRead = reader.read(buf);
					} catch (IOException e) {
						e.printStackTrace();
						return "";
					}
				if (numRead == -1)
					break;
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
			}
			try {
			reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						return "";
					}
			return fileData.toString();
		}
}