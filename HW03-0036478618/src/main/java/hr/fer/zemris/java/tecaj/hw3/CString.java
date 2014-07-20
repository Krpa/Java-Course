package hr.fer.zemris.java.tecaj.hw3;

/**
 * 
 * Implementation of immutable string. <br>
 * Stores private char array and 2 integers, offset and length.
 * @author Ivan Krpelnik
 */

public class CString {

	private char[] data;
	private int offset;
	private int length;
	
	
	/**
	 * Constructs new CString with given char array, offset and length.
	 * @throw {@link NullPointerException} if:<br> data == null
	 * @throw {@link IllegalArgumentException} if:<br> offset + length > data.length
	 * @param data - char array
	 * @param offset - begin index
	 * @param length - new length
	 */
	public CString(char[] data, int offset, int length) {
		if(data == null) {
			throw new NullPointerException();
		}
		if(offset + length > data.length) {
			throw new IllegalArgumentException();
		}
		
		this.data = new char[data.length];
		
		for(int i = 0; i < data.length; ++i) {
			this.data[i] = data[i];
		}
		this.offset = offset;
		this.length = length;
	}
	
	/**
	 * Constructs new CString with given char array. <br>
	 * Sets offset to 0 and length to length of given char array.
	 * @throw {@link NullPointerException} if: <br> data == null
	 * @param data - char array
	 */
	public CString(char[] data) {
		if(data == null) {
			throw new NullPointerException();
		}
		this.offset = 0;
		this.length = data.length;
		this.data = new char[length];
		for(int i = 0; i < length; ++i) {
			this.data[i] = data[i];
		}
	}

	/**
	 * Constructs new CString from given CString.
	 * If char array of given CString is larger than needed, new instance stores a copy of that array. <br>
	 * Otherwise, new CString uses original char array. 
	 * @throw {@link NullPointerException} if: <br> original == null
	 * @param CString original
	 */
	public CString(CString original) {
		if(original == null) {
			throw new NullPointerException();
		}
	
		if(original.data.length == original.length) {
			this.offset = original.offset;
			this.length = original.length;
			this.data = original.data;
		}
		
		else {
			this.data = new char[original.length];
			for(int i = 0; i < original.length; ++i) {
				this.data[i] = original.charAt(i);
			}
			
			this.offset = 0;
			this.length = original.length;
		}
	}
	
	/**
	 * Constructs new CString from given String.
	 * @param String s
	 */
	public CString(String s) {
		if(s == null) {
			throw new NullPointerException();
		}
		
		this.length = s.length();
		this.offset = 0;
		this.data = new char[this.length];
		
		for(int i = 0; i < this.length; ++i) {
			this.data[i] = s.charAt(i);
		}
	}
	
	/**
	 * Private constructor.
	 * @param CString cs
	 * @param offset
	 * @param length
	 */
	private CString(CString cs, int offset, int length) {
		this.data = cs.data;
		this.offset = offset;
		this.length = length;
	}
	
	/**
	 * Returns length of this CString.
	 * @return length
	 */
	public int length() {
		return this.length;
	}
	
	/**
	 * Returns char at given index in this CString.
	 * @throw {@link IndexOutOfBoundsException} if: <br> index >= length
	 * @param index
	 * @return char
	 */
	public char charAt(int index) {
		if(index >= length) {
			throw new IndexOutOfBoundsException();
		}
		return this.data[offset+index];
	}
	
	/**
	 * Returns a copy of this CString's char array.
	 * @return char array
	 */
	public char[] toCharArray() {
		char[] ret = new char[length];
		for(int i = 0; i < length; ++i) {
			ret[i] = this.charAt(i);
		}
		return ret;
	}
	
	/**
	 * Returns string representation of this CString.
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < length; ++i) {
			buf.append(this.charAt(i));
		}
		return buf.toString();
	}
	
	/**
	 * Returns index of first occurrence of a given char in this CString. <br> 
	 * @param c
	 * @return 
	 * 		-1 if char is not found. <br>
	 * 		Index of first occurrence if given char is found.
	 */
	public int indexOf(char c) {
		
		for(int i = 0; i < length; ++i) {
			if(data[i] == c) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Checks if this CString start with given CString.
	 * @param s - given CString
	 * @return
	 * 		true if this CString starts with given CString, <br>
	 * 		false otherwise.
	 */
	public boolean startsWith(CString s) {
		if(s.length > this.length) {
			return false;
		}
		
		for(int i = 0; i < s.length; ++i) {
			if(this.charAt(i) != s.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if this CString ends with given CString.
	 * @param s - given CString
	 * @return
	 * 		true if this CString ends with given CString, <br>
	 * 		false otherwise
	 */
	public boolean endsWith(CString s) {
		if(s.length > this.length) {
			return false;
		}
		
		for(int i = 1; i <= s.length; ++i) {
			if(this.charAt(this.length - i) != s.charAt(s.length - i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if this CString contains given CString.
	 * @param s given CString
	 * @return 
	 * 		true if this string contains given string at any position, <br>
	 * 		false otherwise
	 */
	public boolean contains(CString s) {
		if(s.length > this.length) {
			return false;
		}
		
		for(int i = 0; i <= this.length - s.length; ++i) {
			boolean b = true;
			for(int j = 0; j < s.length; ++j) {
				b &= this.charAt(i+j) == s.charAt(j);
			}
			if(b == true) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns new CString substring of this CString.
	 * @throw {@link IndexOutOfBoundsException} if: <br> endIndex < startIndex or startIndex < 0 or endIndex > length
	 * @param startIndex
	 * @param endIndex
	 * @return new CString
	 */
	public CString substring(int startIndex, int endIndex) {
		
		if(endIndex < startIndex || startIndex < 0 || endIndex > length) {
			throw new IndexOutOfBoundsException();
		}
		
		return new CString(this, offset + startIndex, endIndex - startIndex);
	}
	
	/**
	 * Returns new CString which represents starting part of original string and is of length n.
	 * @throw {@link IndexOutOfBoundsException} if: <br> n > this.length or n < 0
	 * @param n given length
	 * @return new CString
	 */
	public CString left(int n) {
		if(n > this.length || n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		return new CString(this, this.offset, n);
	}
	
	/**
	 * Returns new CString which represents ending part of original string and is of length n.
	 * @throw {@link IndexOutOfBoundsException} if: <br> n > this.length  or  n < 0
	 * @param n
	 * @return
	 */
	public CString right(int n) {
		if(n > this.length || n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		return new CString(this, this.offset + length - n, n);
	}
	
	/**
	 * Returns new CString which is concatenation of current and given CString.
	 * @throw {@link NullPointerException} if: <br> s == null
	 * @param s given CString
	 * @return new CString
	 */
	public CString add(CString s) {
		if(s == null) {
			throw new NullPointerException();
		}
		char temp[] = new char[this.length + s.length];
		
		for(int i = 0; i < this.length; ++i)
			temp[i] = this.charAt(i);
		
		for(int i = 0; i < s.length; ++i)
			temp[this.length + i] = s.charAt(i);
		
		return new CString(temp);
	}
	
	/**
	 * Returns new CString in which each occurrence of oldChar is replaced with newChar.
	 * @param oldChar
	 * @param newChar
	 * @return new CString
	 */
	public CString replaceAll(char oldChar, char newChar) {
		
		char temp[] = new char[this.length];
		
		for(int i = 0; i < this.length; ++i) {
			if(this.charAt(i) == oldChar)
				temp[i] = newChar;
			else
				temp[i] = this.charAt(i);
		}
		
		return new CString(temp);
	}
	
	/**
	 * Returns new CString in which each occurrence of old substring is replaced with the new substring.
	 * @param oldStr - old substring
	 * @param newStr - new substring
	 * @return new CString
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		
		if(oldStr == null || newStr == null) {
			throw new NullPointerException();
		}
		
		if(oldStr.length == 0) {
			throw new IllegalArgumentException("oldStr.length must not be 0.");
		}
		
		
		int size = 0;
		
		for(int i = 0; i < this.length; ++i) {
			int j;
			for(j = 0; j < oldStr.length && i + j < this.length; ++j) {
				if(this.charAt(i+j) != oldStr.charAt(j)) {
					break;
				}
			}
			if(j == oldStr.length) {
				size += newStr.length;
				i += oldStr.length - 1;
			}
			else {
				size++;
			}
		}
		
		char temp[] = new char[size];
		int index = 0;
		for(int i = 0; i < this.length; ++i) {
			int j;
			for(j = 0; j < oldStr.length && i + j < this.length; ++j) {
				if(this.charAt(i+j) != oldStr.charAt(j)) {
					break;
				}
			}
			if(j == oldStr.length) {
				for(j = 0; j < newStr.length; ++j) {
					temp[index++] = newStr.charAt(j);
				}
				i += oldStr.length - 1;
			}
			else {
				temp[index++] = this.charAt(i);
			}
		}
		
		return new CString(temp);
	}
}
