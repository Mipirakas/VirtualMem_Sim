package be.virtualmem.logic.process.memory.table;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.memory.Page;
import be.virtualmem.logic.process.memory.entry.PageTableEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageTableStructureTest {
    PageTableStructure pageTableStructure;
    Address address = Address.fromHexToAddress("0xa8caa15ca000");

    @BeforeEach
    void setUp() {
        pageTableStructure = new PageTableStructure(4);
    }

    @Test
    void getPageTableEntry() {
        PageTableEntry entry = pageTableStructure.getPageTableEntry(address);

        Assertions.assertNull(entry);
    }

    @Test
    void mapPageTables() {
        Page page = new Page(address, 12);
        List<Page> pageList = new ArrayList<>();
        pageList.add(page);

        pageTableStructure.mapPageTables(pageList);
        PageTable pageTable = pageTableStructure.getBaseTable();

        PageTableEntry entry = pageTableStructure.getPageTableEntry(address);

        Assertions.assertNotNull(entry);
    }

    @Test
    void unmapPageTables() {
    }

    @Test
    void getPhysicalAddress() {
    }
}