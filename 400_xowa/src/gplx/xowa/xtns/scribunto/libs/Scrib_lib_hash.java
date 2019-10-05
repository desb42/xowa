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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.scribunto.procs.*;
import org.getopt.util.hash.*;
import java.util.zip.*;
public class Scrib_lib_hash implements Scrib_lib {
	public Scrib_lib_hash(Scrib_core core) {this.core = core;} private Scrib_core core;
	public String Key() {return "mw.hash";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_hash(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.hash.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_listAlgorithms:							return listAlgorithms(args, rslt);
			case Proc_hashValue:						return hashValue(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_listAlgorithms = 0, Proc_hashValue = 1;
	public static final String 
	  Invk_listAlgorithms = "listAlgorithms", Invk_hashValue = "hashValue"
	;
	private static final	String[] Proc_names = String_.Ary(Invk_listAlgorithms, Invk_hashValue);
	public boolean listAlgorithms(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// validate args
		if (args.Len() != 0) return rslt.Init_obj(null);	// no args
		return rslt.Init_obj(null);
	}
	public boolean hashValue(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// validate args
		if (args.Len() != 2) return rslt.Init_obj(null);	// 2 args
		byte[] algo = args.Xstr_bry_or_null(0);
		byte[] value = args.Xstr_bry_or_null(1);
		int len = algo.length;
		if (len < 3)
			throw Err_.new_unhandled(0);
		switch (algo[0]) {
			case 'a': // adler32
				if (len == 7 && algo[1] == 'd' && algo[2] == 'l' && algo[3] == 'e' && algo[3] == 'r' && algo[3] == '3' && algo[3] == '2')
					return rslt.Init_obj(Adler32(value));
				break;
 			case 'c': // crc32, crc32b
 				if (len > 4 && algo[1] == 'r' && algo[2] == 'c' && algo[3] == '3' && algo[4] == '2') {
 					if (len == 5)
 						return rslt.Init_obj(Crc32(value));
 					if (len == 6 && algo[5] == 'b')
 						return rslt.Init_obj(Crc32b(value));
 				}
 				break;
			case 'f':
				if (len > 5 && algo[1] == 'n' && algo[2] == 'v' && algo[3] == '1') {
					switch (algo[4]) {
						case '3':
							if (len == 6 && algo[5] == '2')
								return rslt.Init_obj(Fnv132(value));
						case '6':
							if (len == 6 && algo[5] == '4')
								return rslt.Init_obj(Fnv164(value));
						case 'a':
							if (len == 7) {
								if (algo[5] == '3' && algo[6] == '2')
									return rslt.Init_obj(Fnv1a32(value));
								if (algo[5] == '6' && algo[6] == '4')
									return rslt.Init_obj(Fnv1a64(value));
							}
						default:
							break;
					}
				}
				break;
			case 'g':
			case 'h':
			case 'j':
			case 'm':
			case 'r':
			case 's':
			case 't':
			case 'w':
			default:
				break;
		}
		throw Err_.new_unhandled(0);
	}

	private String Adler32(byte[] value) {
		Adler32 crc = new Adler32();
		crc.update(value);
		long val = crc.getValue();
		System.out.println("adler32 " + Long.toHexString(val));
		return Long.toHexString(val);
	}
	private String Crc32(byte[] value) {
		CRC32 crc = new CRC32();
		crc.update(value);
		long val = crc.getValue();
		System.out.println("crc32 " + Long.toHexString(val));
		return Long.toHexString(val);
	}
	private String Crc32b(byte[] value) {
            //?????
		FNV1a64 hash = new FNV1a64();
		hash.init(value, 0, value.length);
		long val = hash.getHash();
		System.out.println("FNV1a64" + Long.toHexString(val));
		return Long.toHexString(val);
	}
	private String Fnv164(byte[] value) {
		FNV164 hash = new FNV164();
		hash.init(value, 0, value.length);
		long val = hash.getHash();
		System.out.println("FNV164" + Long.toHexString(val));
		return Long.toHexString(val);
	}
	private String Fnv132(byte[] value) {
		FNV132 hash = new FNV132();
		hash.init(value, 0, value.length);
		long val = hash.getHash();
		System.out.println("FNV132" + Long.toHexString(val));
		return Long.toHexString(val);
	}
	private String Fnv1a32(byte[] value) {
		FNV1a32 hash = new FNV1a32();
		hash.init(value, 0, value.length);
		long val = hash.getHash();
		System.out.println("FNV1a32" + Long.toHexString(val));
		return Long.toHexString(val);
	}
	private String Fnv1a64(byte[] value) {
		FNV1a64 hash = new FNV1a64();
		hash.init(value, 0, value.length);
		long val = hash.getHash();
		System.out.println("FNV1a64" + Long.toHexString(val));
		return Long.toHexString(val);
	}

}
