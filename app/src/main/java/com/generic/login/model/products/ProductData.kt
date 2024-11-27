package com.generic.login.model.products

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class ProductData: BaseObservable() {

    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            //notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var sourceURL: String = ""
        set(value) {
            field = value
            //notifyPropertyChanged(BR.price)
        }
}
