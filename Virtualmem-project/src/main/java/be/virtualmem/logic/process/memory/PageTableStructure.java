package be.virtualmem.logic.process.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageTableStructure {
    private Map<Integer, List<IPageTable>> pageTableStructure; // <PageTableLevel, ListWithPageTables>

    public PageTableStructure(int levels) {
        pageTableStructure = new HashMap<>();
        for (int i = 0; i < levels; i++) {
            pageTableStructure.put(i, new ArrayList<>());
        }
    }


}
