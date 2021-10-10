package com.gpfreetech.weather.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gpfreetech.weather.R
import com.gpfreetech.weather.databinding.ActivityMainBinding
import com.gpfreetech.weather.ui.main.data.model.WeatherDataResponse
import com.gpfreetech.weather.ui.main.data.model.WeatherDetail
import com.gpfreetech.weather.ui.main.util.AppConstants
import com.gpfreetech.weather.ui.main.util.AppUtils
import com.gpfreetech.weather.ui.main.view.adapter.WeatherListAdapter
import com.gpfreetech.weather.ui.main.view.viewmodel.WeatherViewModel

import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), WeatherListAdapter.Callback {

    private lateinit var dataBind: ActivityMainBinding

    private lateinit var viewModel: WeatherViewModel

    var adapter: WeatherListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        setupUI()

    }

    private fun setupUI() {
        initializeRecyclerView()
        dataBind.etCity.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(dataBind.etCity.text.trim().isNotEmpty()) {
                    viewModel.getWeatherByCity((view as EditText).text.toString())
                        .observe(this, Observer { newScore ->
                            bindView(newScore!!)
                        })
                }else{
                    dataBind.etCity.error = resources.getString(R.string.enter_your_city_name)
                }
            }
            false
        }

        viewModel.searchedCity.observe(this, Observer {
            adapter?.addList(it)
        })
    }

    private fun initializeRecyclerView() {
        adapter = WeatherListAdapter(this)
        dataBind.recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun clickOnCity(weather: WeatherDetail) {
        viewModel.searchByCity(weather.cityName)
            .observe(this, Observer { item ->
                bindView(item)
            })
    }

    /**
     * Bind data to UI.
     * todo use view binding and data binding here
     */
    private fun bindView(item: WeatherDataResponse) {
        if (item == null) {
            dataBind.layoutSearch.visibility = View.GONE
            dataBind.txtMessage.visibility = View.VISIBLE
            Toast.makeText(
                applicationContext,
                R.string.msg_city_not_found,
                Toast.LENGTH_LONG
            ).show()

        } else {
            dataBind.layoutSearch.visibility = View.VISIBLE
            dataBind.txtMessage.visibility = View.GONE

            dataBind.city.text = "" + item.name
            dataBind.country.text = "" + item.sys.country

            var updatedAtText = "Last Updated at: " + SimpleDateFormat(
                "dd/MM/yyyy hh:mm a",
                Locale.ENGLISH
            ).format(Date(item.dt * 1000L));

            dataBind.time.text = updatedAtText
            dataBind.temp.text = "${item.main.temp}"; //+"$°C"
            dataBind.forecast.text = item.weather[0].description
            dataBind.humidity.text = "${item.main.humidity}"
            dataBind.minTemp.text = "${item.main.tempMin}"
            dataBind.maxTemp.text = "${item.main.tempMax}"
            dataBind.pressure.text = "${item.main.pressure}"
            dataBind.windSpeed.text = "${item.wind.speed}"

            //Long rise = sys.getLong("sunrise");
            var sunrise = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(Date(item.sys.sunrise * 1000L));
            var sunset = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(Date(item.sys.sunset * 1000L));

            dataBind.sunrises.text = sunrise
            dataBind.sunsets.text = sunset


            // save to db
            val weatherDetail = WeatherDetail()
            weatherDetail.id = item.id
            weatherDetail.icon = item.weather.first().icon
            weatherDetail.cityName = item.name.toLowerCase()
            weatherDetail.countryName = item.sys.country
            weatherDetail.temp = item.main.temp
            weatherDetail.dateTime =
                AppUtils.getCurrentDateTime(AppConstants.DATE_FORMAT_1)

            viewModel.addSearchCity(weatherDetail)
        }
    }
}