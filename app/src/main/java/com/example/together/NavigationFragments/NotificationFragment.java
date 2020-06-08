package com.example.together.NavigationFragments;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    ArrayList<Notification> notifications = new ArrayList<>();
    NotificationViewModel notificationViewModel;
    Storage storage ;
    ProgressBar loadingProgressPar;
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout refreshLayout;
    boolean isScrolling=false;
    int currentItem,totalItems,scrolledOutItems;
    int numOfPages=1;
    private static final int loadLimit =10;
    boolean isDone=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        storage=new Storage(getContext());

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        if(HelperClass.checkInternetState(getContext())){
        loadNotifications(1);}
        else {

            HelperClass.showAlert("Error",HelperClass.checkYourCon,getContext());
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification,container,false);
        recyclerView=v.findViewById(R.id.notification_rv);
        loadingProgressPar=v.findViewById(R.id.loadingProgressPar);

        toggleButton = v.findViewById(R.id.togglee_Button);
        //TODO: HERE WE WILL SETUP CONFIG OF NOTIFICATION
      // toggleButton.setChecked(true);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isDone) {
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
                }
                else {
                    isDone=false;
                }
            }

        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            currentItem = mLayoutManager.findLastVisibleItemPosition();
                if(isScrolling&&(currentItem==totalItems-3)&&dy>0){
                    isScrolling=false;
                    Log.i("NOTIFICATIONSMHMD","Loading");

                    loadingProgressPar.setVisibility(View.VISIBLE);
                    if(HelperClass.checkInternetState(getContext())) {
                        loadNotifications(numOfPages);
                    }
                    else {
                        loadingProgressPar.setVisibility(View.INVISIBLE);

                        HelperClass.showAlert("Error",HelperClass.checkYourCon,getContext());
                    }





                }
            }
        });







        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelp itemTouchHelp = new ItemTouchHelp(0,
                ItemTouchHelper.LEFT );

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(itemTouchHelp);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    public void loadNotifications(int page){
        notificationViewModel.getUserNotification(storage.getId(),page,storage.getToken()).observe(this, new Observer<NotificationResponse>() {
            @Override
            public void onChanged(NotificationResponse notificationResponse) {
if(notificationResponse!=null) {
    isScrolling = false;
    notifications.addAll(notificationResponse.getData());
    totalItems = notifications.size();
    adapter.notifyDataSetChanged();

    numOfPages++;
    loadingProgressPar.setVisibility(View.INVISIBLE);
}
else {
    loadingProgressPar.setVisibility(View.INVISIBLE);

    HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,getContext());


}
            }
        });
    }

    private void   enableNotifications(){
        notificationViewModel.enableNotification(storage.getId(),storage.getToken()).observe(this, new Observer<GeneralResponse>() {
            @Override
            public void onChanged(GeneralResponse response) {
                if(response==null){

                    HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,getContext());



                }
                else {

                    Toast.makeText(getContext(),response.response,Toast.LENGTH_LONG).show();


                }
            }
        });

    }
    private void   disableNotifications(){

        notificationViewModel.disableNotification(storage.getId(),storage.getToken()).observe(this, new Observer<GeneralResponse>() {
            @Override
            public void onChanged(GeneralResponse response) {
                if(response==null){

                    HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,getContext());
                }
                else {
                    Toast.makeText(getContext(),response.response,Toast.LENGTH_LONG).show();


                }
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BottomNavigationView)getActivity()).setActionBarTitle("Notification");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new NotificationRecyclarViewAdapter(notifications,getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.notification_status,menu);
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
        boolean isUndoClickedNotClicked = true;




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
                    if(HelperClass.checkInternetState(getContext())) {


                        notifications.remove(notificationIndex);
                        adapter.notifyItemRemoved(notificationIndex);
                        Snackbar.make(recyclerView, "Delete Notification ?", Snackbar.LENGTH_SHORT)
                                .setAction("Undo", v -> {

                                    notifications.add(notificationIndex, deletedNotification);
                                    adapter.notifyItemInserted(notificationIndex);
                                    isUndoClickedNotClicked = false;
                                }).setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {

                                if (isUndoClickedNotClicked) {
                                    notificationViewModel.deleteNotification(deletedNotification.getId(), storage.getToken())
                                            .observe(NotificationFragment.this, new Observer<GeneralResponse>() {
                                                @Override
                                                public void onChanged(GeneralResponse response) {
                                                    if (response != null) {
                                                        Toast.makeText(getContext(), response.response, Toast.LENGTH_LONG).show();
                                                    } else {
                                                        HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,getContext());
                                                        notifications.add(notificationIndex, deletedNotification);
                                                        adapter.notifyItemInserted(notificationIndex);
                                                    }

                                                }
                                            });

                                }
                            }
                        }).show();

                    }
                    else {

                        HelperClass.showAlert("Error",HelperClass.checkYourCon,getContext());
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




}
