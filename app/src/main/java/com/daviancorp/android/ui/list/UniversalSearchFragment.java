package com.daviancorp.android.ui.list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.data.classes.Monster;
import com.daviancorp.android.data.classes.Quest;
import com.daviancorp.android.data.database.MultiObjectCursor;
import com.daviancorp.android.loader.UniversalSearchCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ArmorClickListener;
import com.daviancorp.android.ui.ClickListeners.DecorationClickListener;
import com.daviancorp.android.ui.ClickListeners.ItemClickListener;
import com.daviancorp.android.ui.ClickListeners.MonsterClickListener;
import com.daviancorp.android.ui.ClickListeners.QuestClickListener;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;

import java.io.IOException;
import java.util.HashMap;

public class UniversalSearchFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private interface ResultHandler<T> {
        String getImage(T obj);
        String getName(T obj);
        String getType(T obj);
        View.OnClickListener createListener(T obj);
    }

    private HashMap<Class, ResultHandler> mHandlers = new HashMap<>();

    private String mSearchTerm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandlers.put(Monster.class, new ResultHandler<Monster>() {
            @Override
            public String getImage(Monster obj) {
                return "icons_monster/" + obj.getFileLocation();
            }

            @Override
            public String getName(Monster obj) {
                return obj.getName();
            }

            @Override
            public String getType(Monster obj) {
                return "Monster";
            }

            @Override
            public View.OnClickListener createListener(Monster obj) {
                return new MonsterClickListener(getActivity(), obj.getId());
            }
        });

        mHandlers.put(Quest.class, new ResultHandler<Quest>() {
            @Override
            public String getImage(Quest obj) {
                // todo: Change color if capture/slay (requires db + more icons)
                return "icons_items/Quest-Icon-White.png";
            }

            @Override
            public String getName(Quest obj) {
                return obj.getName();
            }

            @Override
            public String getType(Quest obj) {
                return "Quest";
            }

            @Override
            public View.OnClickListener createListener(Quest obj) {
                return new QuestClickListener(getActivity(), obj.getId());
            }
        });

        mHandlers.put(Item.class, new ResultHandler<Item>() {
            @Override
            public String getImage(Item item) {
                // todo: refactor how images are selected, this is a repeat of ItemListFragment
                String cellImage;
                switch(item.getSubType()){
                    case "Head":
                        cellImage = "icons_armor/icons_head/head" + item.getRarity() + ".png";
                        break;
                    case "Body":
                        cellImage = "icons_armor/icons_body/body" + item.getRarity() + ".png";
                        break;
                    case "Arms":
                        cellImage = "icons_armor/icons_arms/arms" + item.getRarity() + ".png";
                        break;
                    case "Waist":
                        cellImage = "icons_armor/icons_waist/waist" + item.getRarity() + ".png";
                        break;
                    case "Legs":
                        cellImage = "icons_armor/icons_legs/legs" + item.getRarity() + ".png";
                        break;
                    case "Great Sword":
                        cellImage = "icons_weapons/icons_great_sword/great_sword" + item.getRarity() + ".png";
                        break;
                    case "Long Sword":
                        cellImage = "icons_weapons/icons_long_sword/long_sword" + item.getRarity() + ".png";
                        break;
                    case "Sword and Shield":
                        cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + item.getRarity() + ".png";
                        break;
                    case "Dual Blades":
                        cellImage = "icons_weapons/icons_dual_blades/dual_blades" + item.getRarity() + ".png";
                        break;
                    case "Hammer":
                        cellImage = "icons_weapons/icons_hammer/hammer" + item.getRarity() + ".png";
                        break;
                    case "Hunting Horn":
                        cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + item.getRarity() + ".png";
                        break;
                    case "Lance":
                        cellImage = "icons_weapons/icons_lance/lance" + item.getRarity() + ".png";
                        break;
                    case "Gunlance":
                        cellImage = "icons_weapons/icons_gunlance/gunlance" + item.getRarity() + ".png";
                        break;
                    case "Switch Axe":
                        cellImage = "icons_weapons/icons_switch_axe/switch_axe" + item.getRarity() + ".png";
                        break;
                    case "Charge Blade":
                        cellImage = "icons_weapons/icons_charge_blade/charge_blade" + item.getRarity() + ".png";
                        break;
                    case "Insect Glaive":
                        cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + item.getRarity() + ".png";
                        break;
                    case "Light Bowgun":
                        cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + item.getRarity() + ".png";
                        break;
                    case "Heavy Bowgun":
                        cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + item.getRarity() + ".png";
                        break;
                    case "Bow":
                        cellImage = "icons_weapons/icons_bow/bow" + item.getRarity() + ".png";
                        break;
                    default:
                        cellImage = "icons_items/" + item.getFileLocation();
                }

                return cellImage;
            }

            @Override
            public String getName(Item obj) {
                return obj.getName();
            }

            @Override
            public String getType(Item obj) {
                return obj.getType();
            }

            @Override
            public View.OnClickListener createListener(Item obj) {
                switch(obj.getType()){
                    case "Weapon":
                        return new WeaponClickListener(getActivity(), obj.getId());
                    case "Armor":
                        return new ArmorClickListener(getActivity(), obj.getId());
                    case "Decoration":
                        return new DecorationClickListener(getActivity(), obj.getId());
                    default:
                        return new ItemClickListener(getActivity(), obj.getId());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generic_list, container, false);
    }

    public void performSearch(String searchTerm) {
        mSearchTerm = searchTerm;
        if (mSearchTerm != null) {
            mSearchTerm = mSearchTerm.trim();
        }

        if (!mSearchTerm.equals("")) {
            getLoaderManager().restartLoader(0, null, this);
        } else {
            clearSearch();
        }
    }

    private void clearSearch() {
        setListAdapter(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new UniversalSearchCursorLoader(getActivity(), mSearchTerm);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        UniversalSearchCursorAdapter adapter = new UniversalSearchCursorAdapter(
                getActivity(), mHandlers, (MultiObjectCursor) cursor);
        setListAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setListAdapter(null);
    }

    private static class UniversalSearchCursorAdapter extends CursorAdapter {
        private HashMap<Class, ResultHandler> mHandlers;
        public UniversalSearchCursorAdapter(Context context,
                                            HashMap<Class, ResultHandler> handlers,
                                            MultiObjectCursor cursor) {
            super(context, cursor, 0);
            mHandlers = handlers;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            return inflater.inflate(R.layout.fragment_searchresult_listitem,
                    parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Object result = ((MultiObjectCursor)cursor).getObject();
            Class originalClass = result.getClass();

            if (!mHandlers.containsKey(originalClass)) {
                // Not expected, so marked as a runtime exception
                throw new RuntimeException(
                        "Could not find handler for class " + originalClass.getName());
            }

            ResultHandler handler = mHandlers.get(originalClass);

            ImageView imageView = (ImageView) view.findViewById(R.id.result_image);
            TextView nameView = (TextView) view.findViewById(R.id.result_name);
            TextView typeView = (TextView) view.findViewById(R.id.result_type);

            String imagePath = handler.getImage(result);
            if (imagePath != null) {
                Drawable itemImage = null;

                try {
                    itemImage = Drawable.createFromStream(
                            context.getAssets().open(imagePath), null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                imageView.setImageDrawable(itemImage);
            }

            nameView.setText(handler.getName(result));
            typeView.setText(handler.getType(result));
            view.setOnClickListener(handler.createListener(result));
        }
    }
}
