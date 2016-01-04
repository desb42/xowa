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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bit_heap_wtr {
	private final gplx.xowa.htmls.core.hzips.Xoh_hzip_int hzip_int = new gplx.xowa.htmls.core.hzips.Xoh_hzip_int().Mode_is_b256_(true);
	private final Bry_bfr hzip_int_bfr = Bry_bfr.reset_(5);
	public int Cur()		{return cur;} private int cur;
	public int Cur_bits()	{return cur_bits;} private int cur_bits;
	public Bry_bfr Heap()	{return heap;} private final Bry_bfr heap = Bry_bfr.new_();
	public void Clear()		{heap.Clear(); cur = 0; cur_bits = 0;}
	public void Add_bool(boolean v) {
		if (v)
			cur += Pow_ary[cur_bits];
		if (cur_bits == 7) {
			heap.Add_byte((byte)cur);
			cur = 0;
			cur_bits = 0;
		}
		else
			++cur_bits;
	}
	public void Add_byte(byte val_byte) {
		int val_int = val_byte & 0xFF;	// PATCH.JAVA:need to convert to unsigned byte
		if (cur_bits == 0)
			heap.Add_byte(val_byte);
		else {
			heap.Add_byte((byte)(cur + (val_int << cur_bits)));
			this.cur = val_int >> cur_bits;
		}
	}
	public void Add_byte(int val_bits, int val) {
		int total_bits = cur_bits + val_bits;
		int new_val = cur + (val << cur_bits);
		if (total_bits < 8) {
			this.cur_bits = total_bits;
			this.cur = new_val;
		}
		else {
			heap.Add_byte((byte)new_val);
			this.cur = val >> (8 - cur_bits);
			this.cur_bits = total_bits - 8;
		}
	}
	public void Add_int_hzip(int reqd_len, int val) {
		hzip_int.Encode(reqd_len, hzip_int_bfr, val);
		int len = hzip_int_bfr.Len();
		byte[] hzip_bry = hzip_int_bfr.Bfr();
		for (int i = 0; i < len; ++i) {
			byte b = hzip_bry[i];
			Add_byte(8, b & 0xFF);	// PATCH.JAVA:need to convert to unsigned byte
		}
		hzip_int_bfr.Clear();
	}
	public void Save(Bry_bfr bfr) {}
	public static final int[] Pow_ary = new int[] {1, 2, 4, 8, 16, 32, 64, 128, 256};
}