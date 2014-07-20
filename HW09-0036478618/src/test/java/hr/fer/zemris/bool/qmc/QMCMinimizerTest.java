package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class QMCMinimizerTest {
	
	@Test
	public void MinimizerTest1() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		List<Integer> minterms = Arrays.asList(0,1,4,5,11,15);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		false,
		minterms,
		new ArrayList<Integer>()
		);
		MaskBasedBF[] fje = QMCMinimizer.minimize(f1, true);
		System.out.println("Prvi test:");
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for(MaskBasedBF f : fje) {
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
	}
	
	@Test
	public void MinimizerTest2() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		List<Integer> minterms = Arrays.asList(0,1,4,5,9,11,15);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		true,
		minterms,
		new ArrayList<Integer>()
		);
		MaskBasedBF[] fje = QMCMinimizer.minimize(f1, false);
		System.out.println("Drugi test:");
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
	}
	
	@Test
	public void MinimizerTest3() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		List<Integer> minterms = Arrays.asList(0,1,4,5,9,15);
		List<Integer> dontCares = Arrays.asList(11);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		true,
		minterms,
		dontCares
		);
		MaskBasedBF[] fje = QMCMinimizer.minimize(f1, false);
		System.out.println("Treci test:");
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
	}
	
	@Test
	public void MinimizerTest4() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		List<Integer> minterms = Arrays.asList(4,5,6,7,8,9,10,11,13,14);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		true,
		minterms,
		new ArrayList<Integer>()
		);
		MaskBasedBF[] fje = QMCMinimizer.minimize(f1, false);
		System.out.println("Cetvrti test:");
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
	}
	
	@Test
	public void MinimizerTest5() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		List<Integer> minterms = Arrays.asList(4,5,7,9,10,11,13,14);
		List<Integer> dontCares = Arrays.asList(6,8);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		true,
		minterms,
		dontCares
		);
		MaskBasedBF[] fje = QMCMinimizer.minimize(f1, false);
		System.out.println("Peti test:");
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
	}
	
	@Test
	public void MinimizerTest6() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		List<Integer> minterms = Arrays.asList(0,1,4,5,11,15);
		List<Integer> dontCares = Arrays.asList(2,6,10);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		true,
		minterms,
		dontCares
		);
		MaskBasedBF[] fje = QMCMinimizer.minimize(f1, false);
		System.out.println("Sesti test:");
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
	}
}
