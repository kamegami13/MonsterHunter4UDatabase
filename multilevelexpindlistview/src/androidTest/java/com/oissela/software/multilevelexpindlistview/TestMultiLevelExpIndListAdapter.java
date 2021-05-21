package com.oissela.software.multilevelexpindlistview;

import androidx.recyclerview.widget.RecyclerView;
import android.test.AndroidTestCase;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TestMultiLevelExpIndListAdapter extends AndroidTestCase {
    public void testEmptyAdapter() {
        MockAdapter adapter = new MockAdapter();
        assertEquals(0, adapter.getItemCount());

        MockData d = new MockData("1");

        assertFalse(adapter.remove(d));
        assertFalse(adapter.remove(d, true));

        try {
            adapter.collapseGroup(0);
            fail("Should throw an exception");
        } catch (IndexOutOfBoundsException e) {

        }

        try {
            adapter.expandGroup(0);
            fail("Should throw an exception");
        } catch (IndexOutOfBoundsException e) {

        }

        ArrayList<Integer> groups = adapter.saveGroups();
        assertEquals(0, groups.size());

        adapter.restoreGroups(null);
        adapter.restoreGroups(groups);
    }

    public void testAddAllCollapseExpand() {
        List<MockData> data = getDummyData();
        MockAdapter adapter = new MockAdapter();
        adapter.addAll(data);
        assertEquals(data.size(), adapter.getItemCount());

        List<Integer> pos = new ArrayList<Integer>();
        List<Integer> gSizes = new ArrayList<Integer>();
        pos.add(3);
        gSizes.add(1);
        adapter.collapseGroup(pos.get(pos.size() - 1));
        assertGroup(adapter, pos, gSizes);
        pos.add(13);
        gSizes.add(3);
        adapter.collapseGroup(pos.get(pos.size() - 1));
        assertGroup(adapter, pos, gSizes);

        adapter.expandGroup(13);
        pos.remove(pos.size() - 1);
        gSizes.remove(gSizes.size() - 1);
        assertGroup(adapter, pos, gSizes);
        adapter.expandGroup(3);
        pos.remove(pos.size() - 1);
        gSizes.remove(gSizes.size() - 1);
        assertGroup(adapter, pos, gSizes);
    }

    private void assertGroup(MockAdapter adapter, List<Integer> pos, List<Integer> gSizes) {
        int numGroups = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            MockData d = (MockData) adapter.getItemAt(i);
            if (pos.contains(i)) {
                assertTrue(d.isGroup());
                assertTrue(gSizes.get(numGroups) == d.getGroupSize());
                numGroups++;
            } else {
                assertTrue(!d.isGroup());
            }
        }
    }

    private List<MockData> getDummyData() {
        /**
         * hierarchy
         * 0  1
         * 1   1.1
         * 2   1.2
         * 3       1.2.1
         * 4           1.2.1.1
         * 5       1.2.2
         * 6           1.2.2.1
         * 7   1.3
         * 8  2
         * 9   2.1
         * 10      2.1.1
         * 11          2.1.1.1
         * 12              2.1.1.1.1
         * 13              2.1.1.1.2
         * 14  2.2
         * 15      2.2.1
         * 16      2.2.2
         * 17      2.2.3
         */
        List<MockData> data = new ArrayList<MockData>();
        MockData d1 = new MockData("1");
        MockData d1_1 = new MockData("1.1");
        MockData d1_2 = new MockData("1.2");
        MockData d1_2_1 = new MockData("1.2.1");
        MockData d1_2_1_1 = new MockData("1.2.1.1");
        MockData d1_2_2 = new MockData("1.2.2");
        MockData d1_2_2_1 = new MockData("1.2.2.1");
        MockData d1_3 = new MockData("1.3");
        data.add(d1);
        data.add(d1_1);
        data.add(d1_2);
        data.add(d1_2_1);
        data.add(d1_2_1_1);
        data.add(d1_2_2);
        data.add(d1_2_2_1);
        data.add(d1_3);
        d1.addChild(d1_1);
        d1.addChild(d1_2);
        d1.addChild(d1_3);
        d1_2.addChild(d1_2_1);
        d1_2.addChild(d1_2_2);
        d1_2_1.addChild(d1_2_1_1);
        d1_2_2.addChild(d1_2_2_1);

        MockData d2 = new MockData("2");
        MockData d2_1 = new MockData("2.1");
        MockData d2_1_1 = new MockData("2.1.1");
        MockData d2_1_1_1 = new MockData("2.1.1.1");
        MockData d2_1_1_1_1 = new MockData("2.1.1.1.1");
        MockData d2_1_1_1_2 = new MockData("2.1.1.1.2");
        MockData d2_2 = new MockData("2.2");
        MockData d2_2_1 = new MockData("2.2.1");
        MockData d2_2_2 = new MockData("2.2.2");
        MockData d2_2_3 = new MockData("2.2.3");
        data.add(d2);
        data.add(d2_1);
        data.add(d2_1_1);
        data.add(d2_1_1_1);
        data.add(d2_1_1_1_1);
        data.add(d2_1_1_1_2);
        data.add(d2_2);
        data.add(d2_2_1);
        data.add(d2_2_2);
        data.add(d2_2_3);
        d2.addChild(d2_1);
        d2.addChild(d2_2);
        d2_1.addChild(d2_1_1);
        d2_1_1.addChild(d2_1_1_1);
        d2_1_1_1.addChild(d2_1_1_1_1);
        d2_1_1_1.addChild(d2_1_1_1_2);
        d2_2.addChild(d2_2_1);
        d2_2.addChild(d2_2_2);
        d2_2.addChild(d2_2_3);

        return data;
    }

    public static class MockAdapter extends MultiLevelExpIndListAdapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        }
    }

    public static class MockData implements MultiLevelExpIndListAdapter.ExpIndData {
        private final String mValue;
        private List<MockData> mChildren;
        private boolean mIsGroup;
        private int mGroupSize;

        public MockData(String value) {
            mValue = value;
            mChildren = new ArrayList<MockData>();
        }

        @Override
        public List<? extends MultiLevelExpIndListAdapter.ExpIndData> getChildren() {
            return mChildren;
        }

        @Override
        public boolean isGroup() {
            return mIsGroup;
        }

        @Override
        public void setIsGroup(boolean value) {
            mIsGroup = value;
        }

        @Override
        public void setGroupSize(int groupSize) {
            mGroupSize = groupSize;
        }

        public void addChild(MockData child) {
            mChildren.add(child);
        }

        public int getGroupSize() {
            return mGroupSize;
        }
    }
}
