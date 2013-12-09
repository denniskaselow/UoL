package preparation;

import java.util.List;

import entity.SectionWithOffset;

public interface OverlapBuilder {

	void feed(List<SectionWithOffset> sequenceSections, int patternLength, int allowedErrors);

	List<SectionWithOffset> getOverlappingAreas();

	boolean rawSectionsFullyIncluded();
}