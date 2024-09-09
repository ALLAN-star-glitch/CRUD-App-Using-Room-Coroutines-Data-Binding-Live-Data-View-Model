package com.example.curd

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curd.databinding.ActivityMainBinding
import com.example.curd.db.Subscriber
import com.example.curd.db.SubscriberDAO
import com.example.curd.db.SubscriberDatabase
import com.example.curd.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //creating the dao instance
        val dao: SubscriberDAO = SubscriberDatabase.getInstance(application).subscriberDAO

        //creating the subscriber repository instance using the above dao instance
        val repository = SubscriberRepository(dao)

        //creating subscriber view model factory instance using the repository instance above
        val factory = SubscriberViewModelFactory(repository)

        //creating the subscriber view model instance using the factory above
        subscriberViewModel = ViewModelProvider(this,factory)[SubscriberViewModel::class.java]

        binding.myViewModel = subscriberViewModel //assigning the subscribe view model to the data binding object
        binding.lifecycleOwner = this//since we are intending to use live data with data binding


        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.observe(this){subscribers->
            //Log.d("MyTAG", subscribers.toString())
            binding.recyclerView.adapter = MyRecyclerViewAdapter(subscribers) { selectedItem: Subscriber ->
                listItemClicked(selectedItem)
            }
        }
    }

    private fun listItemClicked(subscriber: Subscriber){
        Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
    }
}