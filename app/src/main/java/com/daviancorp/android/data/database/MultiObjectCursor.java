package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.MergeCursor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A cursor that performs searches across the entire application.
 * Delegates work to other cursors.
 * Created by Carlos on 8/5/2015.
 */
public class MultiObjectCursor extends CursorWrapper {
    public interface Handler<T> {
        Object getObject(T cursor);
    }

    private static class IdentifyingCursorWrapper extends CursorWrapper {
        private MultiObjectCursor parent;
        private Handler handler;

        public IdentifyingCursorWrapper(Cursor cursor, Handler handler) {
            super(cursor);
            this.handler = handler;
        }

        private void setOwner(MultiObjectCursor parent) {
            this.parent = parent;
        }

        @Override
        public boolean moveToPosition(int position) {
            boolean success = super.moveToPosition(position);
            if (success && parent != null) {
                parent.setCurrentCursor(this.getWrappedCursor(), handler);
            }
            return success;
        }
    }

    public static class Builder {
        private List<IdentifyingCursorWrapper> cursors = new ArrayList<IdentifyingCursorWrapper>();

        public <T extends Cursor> void add(T cursor, Handler<T> handler) {
            cursors.add(new IdentifyingCursorWrapper(cursor, handler));
        }

        public <T extends Cursor> void add(T cursor, String methodName) throws NoSuchMethodException {
            final Method method = cursor.getClass().getMethod(methodName);

            cursors.add(new IdentifyingCursorWrapper(cursor, new Handler() {
                @Override
                public Object getObject(Object cursor) {
                    try {
                        return method.invoke(cursor);
                    } catch (Exception ex) {
                        // an exception is unexpected, so use a runtime exception
                        throw new RuntimeException(ex);
                    }
                }
            }));
        }

        public MultiObjectCursor create() {
            IdentifyingCursorWrapper[] cursorsArr = cursors.toArray(
                    new IdentifyingCursorWrapper[cursors.size()]);

            // create the wrapper which will contain and register the wrapped cursors
            return new MultiObjectCursor(cursorsArr);
        }
    }

    private Cursor currentCursor;
    private Handler currentHandler;

    private MultiObjectCursor(IdentifyingCursorWrapper[] cursors) {
        super(new MergeCursor(cursors));

        for (IdentifyingCursorWrapper cursor : cursors) {
            cursor.setOwner(this);
        }

        moveToFirst();
    }

    protected void setCurrentCursor(Cursor cursor, Handler handler) {
        currentCursor = cursor;
        currentHandler = handler;
    }

    public Object getObject() {
        if (currentHandler == null || currentCursor == null) {
            return null;
        }
        return currentHandler.getObject(currentCursor);
    }
}
