package com.daviancorp.android.ui.detail;

public class ArmorSetBuilderDecorationsContainer extends LinearLayout {
    
    private Activity activity;
    private Armor armor;
    private int remainingSockets;
    
    private ImageView[] decorationIcons;
    private TextButton buttonAdd;
    
    public ArmorSetBuilderDecorationsContainer(Context context, AttributeSet attrs, Armor armor) {
        super(context, attrs);
        
        if (!(activity instanceof Activity.class)) {
            throw new InvalidArgumentException("The context argument must extend Activity.");
        }
        
        this.activity = (Activity) context;
        this.armor = armor; // TODO: Set sockets variable
                                               
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = inflater.inflate(R.layout.view_armor_set_builder_decorations_container, null);
        
        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) view.getChildAt(0);
        decorationIcons[1] = (ImageView) view.getChildAt(1);
        decorationIcons[2] = (ImageView) view.getChildAt(2);
        buttonAdd = (TextButton) view.getChildAt(3);
        
        for (ImageView imageView : decorationIcons) {
            // imageView.setDrawable(); SET IT TO THE EMPTY DECORATION ICON
        }
        
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            addDecoration;
        });
    }
    
    private void addDecoration() {
        Intent i = new Intent(activity.getApplicationContext(), DecorationListActivity.class);
        i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER);
        
        activity.startActivityForResult(i);
    }
}