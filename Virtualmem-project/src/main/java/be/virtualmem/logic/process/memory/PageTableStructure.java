package be.virtualmem.logic.process.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageTableStructure {
    private Map<Integer, List<IPageTable>> pageTables; // <PageTableLevel, ListWithPageTables>

    public PageTableStructure(int levels) {
        pageTables = new HashMap<>();
        for (int i = 0; i < levels; i++) {
            pageTables.put(i, new ArrayList<>());
        }
    }
}
