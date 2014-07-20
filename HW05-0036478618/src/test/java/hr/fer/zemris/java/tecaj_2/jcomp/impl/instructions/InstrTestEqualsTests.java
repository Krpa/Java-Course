package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InstrTestEqualsTests {

	@Test
	public void testInstrTestEquals1() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(0)).thenReturn(null);
		when(r.getRegisterValue(1)).thenReturn(null);
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		InstrTestEquals instr = new InstrTestEquals(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(3)).getRegisters();
		verify(r, times(1)).getRegisterValue(0);
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).setFlag(true);
	}
	
	@Test
	public void testInstrTestEquals2() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(0)).thenReturn(new String("bla"));
		when(r.getRegisterValue(1)).thenReturn(new String("bla"));
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		InstrTestEquals instr = new InstrTestEquals(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(3)).getRegisters();
		verify(r, times(1)).getRegisterValue(0);
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).setFlag(true);
	}
	
	@Test
	public void testInstrTestEquals3() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(0)).thenReturn(new Integer(2));
		when(r.getRegisterValue(1)).thenReturn(new Integer(2));
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		InstrTestEquals instr = new InstrTestEquals(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(3)).getRegisters();
		verify(r, times(1)).getRegisterValue(0);
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).setFlag(true);
	}
	
	@Test
	public void testInstrTestEquals4() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(0)).thenReturn(new Integer(1));
		when(r.getRegisterValue(1)).thenReturn(new String("1"));
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		InstrTestEquals instr = new InstrTestEquals(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(3)).getRegisters();
		verify(r, times(1)).getRegisterValue(0);
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).setFlag(false);
	}
	
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrTestEQException1() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(1));
		lista.add(new NumberArgument(2));
		
		new InstrTestEquals(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrTestEQException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new NumberArgument(0));
		lista.add(new RegisterArgument(1));
		
		new InstrTestEquals(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrTestEQException3() {
		List<InstructionArgument> lista = new ArrayList<>();
		new InstrTestEquals(lista);
	}
}
