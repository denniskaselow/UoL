package common.datastructure;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public abstract class ReferenceIndexStructureTest {

	protected final ReferenceIndexStructure indexStructure;

	public ReferenceIndexStructureTest(final ReferenceIndexStructure indexStructure) {
		this.indexStructure = indexStructure;
	}

	@Test(expected = IllegalArgumentException.class)
	public void initRequiresStringInstance() {
		indexStructure.init(null);
	}

	@Test(expected = IllegalStateException.class)
	public void initRequiredBeforeInvokingIndicesOf() {
		indexStructure.indicesOf("AC");
	}

	@Test(expected = IllegalStateException.class)
	public void initRequiredBeforeInvokingSubstring() {
		indexStructure.substring(1, 3);
	}

	@Test(expected = IllegalStateException.class)
	public void initRequiredBeforeInvokingFindLongestCommonSubstring() {
		indexStructure.findLongestPrefixSuffixMatch("AC");
	}

	@Test
	@Parameters
	public void indicesOf(final String sequence, final String query, final Integer[] indices) {
		indexStructure.init(sequence);

		final List<Integer> result = indexStructure.indicesOf(query);

		assertThat(result, hasSize(indices.length));
		assertThat(result, hasItems(indices));
	}

	Object[] parametersForIndicesOf() {
		return $($("GGGGGGGGGGGGGGAC", "AC", new Integer[] { 14 }), //
				$("ACGGGGGGGGGGGGGG", "AC", new Integer[] { 0 }), //
				$("ACGGGGGGGGGGGGAC", "AC", new Integer[] { 0, 14 }), //
				$("GGGGGGGACGGGGGGG", "AC", new Integer[] { 7 }), //
				$("GGGGGACACACGGGGG", "AC", new Integer[] { 5, 7, 9 }), //
				$("GGGGGACGGACGGGGG", "AC", new Integer[] { 5, 9 }), //
				$("GGGGGACGGACGGGGG", "GG", new Integer[] { 0, 1, 2, 3, 7, 11, 12, 13, 14 }), //
				$("GGGGGACACACGGGGG", "CA", new Integer[] { 6, 8 }), //
				$("GGCGGACACTCACGGG", "CA", new Integer[] { 6, 10 }), //
				$("GGGGGACACACGGGGG", "TTTT", new Integer[] {}), //
				$("AGAGATAGCAGAT", "GAG", new Integer[] { 1 }), //
				$("AGAGATAGCAGAT", "AGA", new Integer[] { 0, 2, 9 }), //
				$("GGGGGACACACGGGGG", "GACACACGGGG", new Integer[] { 4 }), //
				$("GGGGGACACACGGGGG", "GGGGGACACACGGGGG", new Integer[] { 0 }), //
				$("G", "G", new Integer[] { 0 }), //
				$("G", "GG", new Integer[] {}) //
		);
	}

	@Test
	public void substring() {
		indexStructure.init("ACTAC");

		assertThat(indexStructure.substring(1, 3), is("CTA"));
	}

	@Test
	public void findLongestPrefixSuffixMatch() {
		indexStructure.init("ACTAC");

		final HasIndexAndLength longestCommonSubstring = indexStructure.findLongestPrefixSuffixMatch("TAC");

		assertThat(longestCommonSubstring.getIndex(), is(2));
		assertThat(longestCommonSubstring.getLength(), is(3));
	}

}