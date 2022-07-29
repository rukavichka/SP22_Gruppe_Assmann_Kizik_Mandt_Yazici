package com.example.View;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.LectureAdapter;
import com.example.Adapter.RoomAdapter;
import com.example.Model.Data;
import com.example.Model.Date;
import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.Service.FetchRooms;
import com.example.readdatabase.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RoomSearchPageFragment extends Fragment {
    private Data data = Data.getInstance();
    private ProgressBar progressBar;
    ImageButton filterButton;
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ArrayList<Room> rooms;
    private Date selectedDate;
    private SearchView searchView;
    TextView searchHeader;
    TextView dateHeader;
    FetchRooms fetchRooms;
    private boolean isOnlyFreeRooms = false;
    private TextView headerTitle;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_lecture_search_page, container, false);
        progressBar = root.findViewById(R.id.progress_loader);
        filterButton = root.findViewById(R.id.filterButton);
        searchHeader = root.findViewById(R.id.resultTextView);
        headerTitle = ((MainActivity)root.getContext()).findViewById(R.id.main_title);
        headerTitle.setText(R.string.main_title_search_rooms);
        TextView searchHeader = root.findViewById(R.id.resultTextView);
        searchHeader.setText(R.string.rooms_little_header);
        searchView = root.findViewById(R.id.room_search);
        fetchRooms = new FetchRooms();
        fetchRooms.setWeakReference(this);
        fetchRooms.execute();
        setFilterButton();
        selectedDate = new Date(LocalDate.now());
        dateHeader = root.findViewById(R.id.selectedDateTextView);
        dateHeader.setText(selectedDate.toString());
        dateHeader.setVisibility(View.VISIBLE);
        searchWidget();
        return root;
    }

    private void setFilterButton() {
        PopupMenu popupMenu = new PopupMenu(getContext(), filterButton);
        popupMenu.inflate(R.menu.room_filter_menu);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.filter_free_rooms:
                                boolean flag = menuItem.isChecked();
                                if (flag) searchHeader.setText(R.string.rooms_little_header);
                                else searchHeader.setText(R.string.rooms_free_header);
                                showOnlyFreeRooms(!flag);
                                menuItem.setChecked(!flag);
                                break;
                            case R.id.filter_date:
                                showCalendar();
                                break;
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void showOnlyFreeRooms(boolean flag){
        isOnlyFreeRooms = flag;
        if (flag){
            ArrayList<Room> freeRooms = new ArrayList<>();
            for(Room room: this.rooms){
                if(room.isFree(selectedDate)) {
                    freeRooms.add(room);
                }
            }
            recyclerViewLecture(freeRooms);
        }
        else {
            recyclerViewLecture(rooms);
        }
    }

    private void showCalendar(){
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        selectedDate  = new Date(materialDatePicker.getHeaderText());
                        selectedDate.setTime(14, 10);
                        dateHeader.setText(selectedDate.toString());
                        showOnlyFreeRooms(isOnlyFreeRooms);
                    }
                });
    }


    public void recyclerViewLecture(ArrayList<Room> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.searchListView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new RoomAdapter(data, selectedDate);
        recyclerViewList.setAdapter(adapter);
        hideProgressBar();
    }


    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void searchWidget() {
        searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Room> searchedRooms = new ArrayList<>();
                for (Room room: rooms) {
                    if (room.getRoomNumber().contains(s.toUpperCase(Locale.ROOT))) {
                        searchedRooms.add(room);
                    }
                }
                recyclerViewLecture(searchedRooms);
                return false;
            }
        });
    }
}
