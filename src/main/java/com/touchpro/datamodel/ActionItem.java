package com.touchpro.datamodel;
@SuppressWarnings("all")
public class ActionItem {
    public static final int ACTION_TYPE_APP = 1000;
    private int action;
    private String actionName;
    private int iconID;
    private String packageName;
    private int value;

    public ActionItem(final int action, final String actionName, final int iconID) {
        super();
        this.value = -1;
        this.action = action;
        this.actionName = actionName;
        this.iconID = iconID;
    }

    public ActionItem(final int action, final String actionName, final int iconID, final int value) {
        super();
        this.value = -1;
        this.action = action;
        this.actionName = actionName;
        this.iconID = iconID;
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        return this.action == ((ActionItem) o).getAction();
    }

    public int getAction() {
        return this.action;
    }

    public int getIconID() {
        return this.iconID;
    }

    public String getName() {
        return this.actionName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return this.action;
    }

    public void setAction(final int action) {
        this.action = action;
    }

    public void setIconID(final int iconID) {
        this.iconID = iconID;
    }

    public void setName(final String actionName) {
        this.actionName = actionName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}