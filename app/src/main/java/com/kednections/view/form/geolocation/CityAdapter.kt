package com.kednections.view.form.geolocation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kednections.domain.models.City

class CityAdapter(context: Context, private val cities: List<City>) :
    ArrayAdapter<City>(context, android.R.layout.simple_dropdown_item_1line, cities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val city = cities[position]
        (view as TextView).text = city.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val city = cities[position]
        (view as TextView).text = city.name
        return view
    }
}