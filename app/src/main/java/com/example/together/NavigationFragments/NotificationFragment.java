package com.example.together.NavigationFragments;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.telephony.CellSignalStrength;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.together.Adapters.NotificationRecyclarViewAdapter;
import com.example.together.BottomNavigationView;
import com.example.together.R;
import com.example.together.data.api.notificaton_apis.NotificationResponse;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Notification;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.NotificationViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    NotificationRecyclarViewAdapter adapter;
    ToggleButton toggleButton;
    TextView alertTv;
    ArrayList<Notification> notifications = new ArrayList<>();
    NotificationViewModel notificationViewModel;
    Storage storage;
    ProgressBar loadingProgressPar;
    LinearLayoutManager mLayoutManager;
    boolean isScrolling = false;
    int currentItem, totalItems;
    int numOfPages = 1;
    boolean isDone = false;
    boolean isConfig = false;
    int lastPageSize=0;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        storage = new Storage(getContext());

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        if (HelperClass.checkInternetState(getContext())) {
            loadNotifications(1);
        } else {

            HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = v.findViewById(R.id.notification_rv);
        loadingProgressPar = v.findViewById(R.id.loadingProgressPar);
        toggleButton = v.findViewById(R.id.togglee_Button);
        alertTv = v.findViewById(R.id.alert_tv);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isDone && isConfig) {
                    if (isChecked) {
                        if (HelperClass.checkInternetState(getContext())) {
                            notificationViewModel.enableNotification(storage.getId(), storage.getToken()).observe(getViewLifecycleOwner(), new Observer<GeneralResponse>() {
                                @Override
                                public void onChanged(GeneralResponse response) {
                                    if (response == null) {


                                        HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                                        isDone = true;
                                        buttonView.toggle();


                                    } else {

                                        Toast.makeText(getContext(), response.response, Toast.LENGTH_LONG).show();


                                    }

                                }
                            });


                        } else {
                            Toast.makeText(getContext(), HelperClass.checkYourCon, Toast.LENGTH_LONG).show();
                            buttonView.setChecked(false);
                        }
                    } else {
                        if (HelperClass.checkInternetState(getContext())) {

                            notificationViewModel.disableNotification(storage.getId(), storage.getToken()).observe(getViewLifecycleOwner(), new Observer<GeneralResponse>() {
                                @Override
                                public void onChanged(GeneralResponse response) {
                                    if (response == null) {

                                        HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                                        isDone = true;
                                        buttonView.toggle();


                                    } else {
                                        Toast.makeText(getContext(), response.response, Toast.LENGTH_LONG).show();


                                    }
                                }
                            });


                        } else {
                            Toast.makeText(getContext(), HelperClass.checkYourCon, Toast.LENGTH_LONG).show();
                            buttonView.setChecked(true);
                        }
                    }
                } else {
                    isDone = false;
                }
            }

        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem = mLayoutManager.findLastVisibleItemPosition();
                if (isScrolling && (currentItem == totalItems-1) && dy > 0) {
                    isScrolling = false;
                    Log.i("NOTIFICATIONSMHMD", "Loading");

                    loadingProgressPar.setVisibility(View.VISIBLE);
                    if (HelperClass.checkInternetState(getContext())) {
                        if(lastPageSize==10) {
                            loadNotifications(numOfPages);
                        }
                        else {loadingProgressPar.setVisibility(View.INVISIBLE);}
                    } else {
                        loadingProgressPar.setVisibility(View.INVISIBLE);

                        HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());
                    }


                }
            }
        });

         refreshLayout=v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               isScrolling = false;
               currentItem=0;
               totalItems=0;
                 numOfPages = 1;
                 isDone = false;
                 isConfig = false;
                 lastPageSize=0;
                 notifications.clear();
                loadNotifications(1);

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelp itemTouchHelp = new ItemTouchHelp(0,
                ItemTouchHelper.LEFT);

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(itemTouchHelp);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    public void loadNotifications(int page) {
        notificationViewModel.getUserNotification(storage.getId(), page, storage.getToken()).observe(this, new Observer<NotificationResponse>() {

            @Override
            public void onChanged(NotificationResponse notificationResponse) {

                if (notificationResponse != null) {


                    if (page == 1) {

                        if (notificationResponse.getStatus() == 1) {
                            toggleButton.setChecked(true);
                            isConfig = true;
                        } else {
                            toggleButton.setChecked(false);
                            isConfig = true;
                        }

                    }
                    isScrolling = false;
                    notifications.addAll(notificationResponse.getData());
                    if (notifications.size() == 0) {
                        alertTv.setVisibility(View.VISIBLE);
                    }

                    totalItems = notifications.size();
                    adapter.notifyDataSetChanged();
                    lastPageSize=notificationResponse.getData().size();
                if(notificationResponse.getData().size()==10) {
                     numOfPages++;
                          }
                    loadingProgressPar.setVisibility(View.INVISIBLE);
                } else {
                    loadingProgressPar.setVisibility(View.INVISIBLE);

                    HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());


                }
                refreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BottomNavigationView) getActivity()).setActionBarTitle("Notification");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationRecyclarViewAdapter(notifications, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.notification_status, menu);
//         final TextView status = menu.findItem(R.id.switch_btn).getActionView().findViewById(R.id.status);
//        final Switch sw = menu.findItem(R.id.switch_btn).getActionView().findViewById(R.id.notification_sw);
//        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    status.setText("On");
//                }
//                else{
//                    status.setText("Off");
//                }
//            }
//        });
    }


    class ItemTouchHelp extends ItemTouchHelper.SimpleCallback {


        public ItemTouchHelp(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c,
                    recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.white))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int notificationIndex = viewHolder.getAdapterPosition();
            Notification deletedNotification;
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedNotification = notifications.get(notificationIndex);
                    notifications.remove(notificationIndex);
                    adapter.notifyItemRemoved(notificationIndex);
                    if (HelperClass.checkInternetState(getContext())) {


                        showYesNoAlert("Delete", "Do you really want to delete this notification?", deletedNotification, notificationIndex);


                    } else {

                        HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());
                        notifications.add(notificationIndex, deletedNotification);
                        adapter.notifyItemInserted(notificationIndex);
                    }


                    break;
//                case ItemTouchHelper.RIGHT:
//
//                    break;
            }
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
    }

    public void showYesNoAlert(String description, String msg, Notification deletedNotification, int notificationIndex) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertView = inflater.inflate(R.layout.custom_yes_no_dialouge, null);
        builder.setView(alertView);
        TextView alertDescription = alertView.findViewById(R.id.alert_description_edit_text);
        TextView alertMessage = alertView.findViewById(R.id.alert_message_edit_text);
        alertDescription.setText(description);
        alertMessage.setText(msg);
        TextView okBtn = alertView.findViewById(R.id.ok_button);
        TextView cancelBtn = alertView.findViewById(R.id.cancle_btn);

        AlertDialog alertDialog = builder.create();

        cancelBtn.setOnClickListener(v -> {

            alertDialog.cancel();
            notifications.add(notificationIndex, deletedNotification);
            adapter.notifyItemInserted(notificationIndex);
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                notificationViewModel.deleteNotification(deletedNotification.getId(), storage.getToken())
                        .observe(NotificationFragment.this, new Observer<GeneralResponse>() {
                            @Override
                            public void onChanged(GeneralResponse response) {
                                if (response != null) {
                                    Toast.makeText(getContext(), response.response, Toast.LENGTH_LONG).show();
                                } else {
                                    HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                                    notifications.add(notificationIndex, deletedNotification);
                                    adapter.notifyItemInserted(notificationIndex);
                                }

                            }
                        });


            }
        });
        alertDialog.show();


    }


}
