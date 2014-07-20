package hr.fer.zemris.java.tecaj.hw3;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for CString class.
 * @author Ivan Krpelnik
 */
public class CStringTest {

	@Test
	public void testToCharArray() {
		CString c1 = new CString("agwapoaijpowjfaage");
		String s = new String("agwapoaijpowjfaage");
		
		char[] c1char = c1.toCharArray();
		char[] schar = s.toCharArray();
		
		for(int i = 0; i < c1char.length; ++i)
			assertEquals(c1char[i], schar[i]);
	}
	@Test
	public void testReplaceAll1() {
		CString c1 = new CString("ababab").replaceAll(new CString("ab"), new CString("abab"));
		String s = new String("ababab").replaceAll("ab", "abab");
		assertEquals(c1.toString(), s);
	}

	@Test
	public void testReplaceAll2() {
		CString c1 = new CString("ababab").replaceAll('a', 'c');
		String s = new String("ababab").replace('a', 'c');
		assertEquals(c1.toString(), s); 
	}
	

	@Test
	public void testReplaceAll3() {
		CString oldCS = new CString("ab");
		CString newCS = new CString("asdf");
		CString c1 = new CString("abacabbbasbac").replaceAll(oldCS, newCS);
		String s = "abacabbbasbac";
		assertEquals(c1.toString(), s.replaceAll("ab", "asdf"));
	}
	
	@Test
	public void testAdd() {
		CString c1 = new CString("bla").add(new CString("bla"));
		String s = new String("bla").concat("bla");
		assertEquals(c1.toString(), s);
	}
	
	@Test
	public void testRight() {
		CString c1 = new CString("agwapoaijpowjfaage");
		for(int i = 0; i < c1.length(); ++i) {
			assertEquals(c1.right(i).toString(), new String("agwapoaijpowjfaage").substring(c1.length()-i));
		}
			
	}
	
	@Test
	public void testLeft() {
		CString c1 = new CString("agwapoaijpowjfaage");
		for(int i = 0; i < c1.length(); ++i) {
			assertEquals(c1.left(i).toString(), new String("agwapoaijpowjfaage").substring(0, i));
		}
			
	}
	
	@Test
	public void testSubstring() {
		CString c1 = new CString("agwapoaijpowjfaage");
		for(int i = 0; i < c1.length(); ++i) {
			for(int j = i; j <= c1.length(); ++j) {
				assertEquals(c1.substring(i, j).toString(), new String("agwapoaijpowjfaage").substring(i, j));
			}
		}	
	}
	
	@Test
	public void testContains() {
		assertEquals(new CString("agwapoaijpowjfaage").contains(new CString("ijpow")), 
				new String("agwapoaijpowjfaage").contains("ijpow"));
		assertEquals(new CString("agwapoaijpowjfaage").contains(new CString("pppp")), 
				new String("agwapoaijpowjfaage").contains("pppp"));
		assertEquals(new CString("agwapoaijpowjfaage").contains(new CString("")), 
				new String("agwapoaijpowjfaage").contains(""));
		assertEquals(new CString("agwapoaijpowjfaage").contains(new CString("agwapoaijpowjfaage")), 
				new String("agwapoaijpowjfaage").contains("agwapoaijpowjfaage"));
	}
	
	@Test
	public void testEndsWith() {
		assertEquals(new CString("agwapoaijpowjfaage").endsWith(new CString("faage")), 
				new String("agwapoaijpowjfaage").endsWith("faage"));
		assertEquals(new CString("agwapoaijpowjfaage").endsWith(new CString("bla")), 
				new String("agwapoaijpowjfaage").endsWith("bla"));
		assertEquals(new CString("agwapoaijpowjfaage").endsWith(new CString("")), 
				new String("agwapoaijpowjfaage").endsWith(""));
		assertEquals(new CString("agwapoaijpowjfaage").endsWith(new CString("agwapoaijpowjfaage")), 
				new String("agwapoaijpowjfaage").endsWith("agwapoaijpowjfaage"));
	}
	
	@Test
	public void testStartsWith() {
		assertEquals(new CString("agwapoaijpowjfaage").startsWith(new CString("agwa")), 
				new String("agwapoaijpowjfaage").startsWith("agwa"));
		assertEquals(new CString("agwapoaijpowjfaage").startsWith(new CString("bla")), 
				new String("agwapoaijpowjfaage").startsWith("bla"));
		assertEquals(new CString("agwapoaijpowjfaage").startsWith(new CString("")), 
				new String("agwapoaijpowjfaage").startsWith(""));
		assertEquals(new CString("agwapoaijpowjfaage").startsWith(new CString("agwapoaijpowjfaage")), 
				new String("agwapoaijpowjfaage").startsWith("agwapoaijpowjfaage"));
	}
	
	@Test
	public void testIndexOf() {
		assertEquals(new CString("agwapoaijpowjfaage").indexOf('p'),
				new String("agwapoaijpowjfaage").indexOf('p'));
		assertEquals(new CString("agwapoaijpowjfaage").indexOf('s'),
				new String("agwapoaijpowjfaage").indexOf('s'));
		assertEquals(new CString("agwapoaijpowjfaage.").indexOf('.'),
				new String("agwapoaijpowjfaage.").indexOf('.'));
	}
	
	@Test
	public void testCharAt() {
		CString c1 = new CString("agwapoaijpowjfaage");
		String s = new String("agwapoaijpowjfaage");
		for(int i = 0; i < c1.length(); ++i) {
			assertEquals(c1.charAt(i), s.charAt(i));
		}
	}
	
	@Test
	public void testEndsWith2() {
		CString c1 = new CString("asgawg");
		CString c2 = new CString("aaaaaaaaaaa");
		assertEquals(false, c1.endsWith(c2));
	}

	@Test
	public void testStartsWith2() {
		CString c1 = new CString("asgawg");
		CString c2 = new CString("aaaaaaaaaaa");
		assertEquals(false, c1.startsWith(c2));
	}
	
	@Test
	public void testContains2() {
		CString c1 = new CString("asgawg");
		CString c2 = new CString("aaaaaaaaaaa");
		assertEquals(false, c1.contains(c2));
	}
	
	@Test
	public void testConstructorLarger() {
		CString c1 = new CString("gawilghahigwhawgiha");
		CString c2 = c1.left(5);
		CString c3 = new CString(c2);
		assertEquals(c2.toString(), c3.toString());
	}
	
	@Test
	public void testConstructorDataOffsetLength() {
		CString c2 = new CString("blablagpeiahgipheag");
		char[] s = new char[c2.length()];
		for(int i = 0; i < c2.length(); ++i)
			s[i] = c2.charAt(i);
		CString c1 = new CString(s, 6, 5);
		assertEquals(c1.toString(), c2.substring(6, 11).toString());
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructorException() {
		CString c1 = null;
		new CString(c1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructorException0() {
		String s = null;
		new CString(s);
	}
	@Test(expected=NullPointerException.class)
	public void testConstructorException1() {
		char[] bla = null;
		new CString(bla, 1, 2);
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructorException2() {
		char[] bla = null;
		new CString(bla);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorDataOffsetLengthException() {
		CString c2 = new CString("blablagpeiahgipheag");
		char[] s = new char[c2.length()];
		for(int i = 0; i < c2.length(); ++i)
			s[i] = c2.charAt(i);
		new CString(s, 6, 50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceAllException2() {
		CString c1 = new CString("bawgawgahhrhar");
		CString c2 = new CString("");
		CString c3 = new CString("gwaga");
		c1.replaceAll(c2, c3);
	}
	
	@Test
	public void testConstructor() {
		CString c1 = new CString("asdf");
		CString c2 = new CString(c1);
		assertEquals(c1.toString(), c2.toString());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testCharAtException() {
		CString c1 = new CString("asdf");
		c1.charAt(15);
	}

	@Test(expected=IndexOutOfBoundsException.class) 
	public void testSubstringException() {
		new CString("asdf").substring(-1, 3);
	}
	
	@Test(expected=IndexOutOfBoundsException.class) 
	public void testLeftException() {
		new CString("asdf").left(10);
	}
	
	@Test(expected=IndexOutOfBoundsException.class) 
	public void testRightException() {
		new CString("asdf").right(10);
	}
	
	@Test(expected=NullPointerException.class)
	public void testAddException() {
		CString c1 = null;
		new CString("asdf").add(c1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testReplaceAllException() {
		CString c1 = null;
		CString c2 = null;
		new CString("asdf").replaceAll(c1, c2);
	}
}