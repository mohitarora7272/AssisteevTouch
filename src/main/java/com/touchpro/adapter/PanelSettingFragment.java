package com.touchpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.touchpro.constant.ACTION;
import com.touchpro.database.DatabaseConstant;
import com.touchpro.datamodel.ActionItem;
import com.touchpro.dialog.SelectActionDialog;
import com.touchpro.dialog.SelectActionDialog.OnActionSelected;
import com.touchpro.easytouch.EasyTouchApplication;
import com.develop.touchpro.R;
import com.touchpro.service.EasyTouchService;

import java.util.ArrayList;
@SuppressWarnings("all")
public class PanelSettingFragment extends Fragment {
    public static final String TAG = "TIME_FRAGMENT";
    private ArrayList actionList;
    private ActionSettingAdapter adapter;
    private GridView gvAction;
    private EasyTouchApplication mApp;
    private int mCurrentPage;

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mApp = (EasyTouchApplication) this.getActivity().getApplicationContext()).loadDataList();
        switch (this.mCurrentPage = this.getArguments().getInt("page", 0)) {
            default: {
            }
            case 0: {
                this.actionList = this.mApp.getActionListMain();
                return;
            }
            case 1: {
                this.actionList = this.mApp.getActionListSetting();
                return;
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(R.layout.panel_fragment_layout, viewGroup, false);
        final TextView textView = (TextView) inflate.findViewById(R.id.panel_fragment_tv_title);
        this.gvAction = (GridView) inflate.findViewById(R.id.panel_fragment_gv_icon);
        switch (this.mCurrentPage) {
            case 0: {
                textView.setText((CharSequence) "1/2 MAIN");
                this.adapter = new ActionSettingAdapter((Context) this.getActivity(), 0, this.actionList);
                this.gvAction.setAdapter((ListAdapter) this.adapter);
                break;
            }
            case 1: {
                textView.setText((CharSequence) "2/2 SETTING");
                this.adapter = new ActionSettingAdapter((Context) this.getActivity(), 0, this.actionList);
                this.gvAction.setAdapter((ListAdapter) this.adapter);
                break;
            }
        }

        this.gvAction.setOnItemClickListener(new OnItemClickListener() {

            class AnonymousClass_1 implements OnActionSelected {
                int position;

                AnonymousClass_1(int i) {
                    this.position = i;
                }

                public void onActionSelected(int i) {
                    PanelSettingFragment.this.actionList.remove(this.position);
                    PanelSettingFragment.this.actionList.add(this.position, (ActionItem) ACTION.getActionList().get(i));
                    switch (PanelSettingFragment.this.mCurrentPage) {
                        case 0 /*0*/:
                            PanelSettingFragment.this.mApp.saveList(DatabaseConstant.LIST_MAIN, PanelSettingFragment.this.actionList);
                            PanelSettingFragment.this.startService();
                            PanelSettingFragment.this.adapter.notifyDataSetChanged();
                            return;
                        case 1 /*1*/:
                            PanelSettingFragment.this.mApp.saveList(DatabaseConstant.LIST_SETTING, PanelSettingFragment.this.actionList);
                            PanelSettingFragment.this.startService();
                            PanelSettingFragment.this.adapter.notifyDataSetChanged();
                            return;
                        default:
                    }
                }
            }

            public void onItemClick(AdapterView adapterview, View view, int i, long l) {
                new SelectActionDialog(PanelSettingFragment.this.getActivity()).setOnActionSelected(new AnonymousClass_1(i));
            }
        });

        /*this.gvAction.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView adapterView, final View view, final int n, final long n2) {
                new SelectActionDialog(PanelSettingFragment.this.getActivity()).setOnActionSelected(new OnActionSelected(n) {
                    int position = this.position;
                    
                    @Override
                    public void onActionSelected(final int n) {
                        PanelSettingFragment.this.actionList.remove(this.position);
                        PanelSettingFragment.this.actionList.add(this.position, ACTION.getActionList().get(n));
                        switch (PanelSettingFragment.this.mCurrentPage) {
                            default: {}
                            case 0: {
                                PanelSettingFragment.this.mApp.saveList("list_main", PanelSettingFragment.this.actionList);
                                PanelSettingFragment.this.startService();
                                PanelSettingFragment.this.adapter.notifyDataSetChanged();
                            }
                            case 1: {
                                PanelSettingFragment.this.mApp.saveList("list_setting", PanelSettingFragment.this.actionList);
                                PanelSettingFragment.this.startService();
                                PanelSettingFragment.this.adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });*/
        return inflate;
    }

    public void startService() {
        final Intent intent = new Intent((Context) this.getActivity(), (Class) EasyTouchService.class);
        intent.setAction("com.touchpro.foregroundservice.action.startforeground");
        this.getActivity().startService(intent);
    }
}