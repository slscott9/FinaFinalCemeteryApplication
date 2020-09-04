package com.example.finalcemeteryproject.utils

import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.Grave

@BindingAdapter("setCemeteryName")
fun TextView.setCemeteryName(item: Cemetery?){
    item?.cemeteryName?.let {
        text = item.cemeteryName
    }
}

@BindingAdapter("setCemeteryLocation")
fun TextView.setCemeteryLocation(item: Cemetery?){
    item?.cemeteryLocation?.let {
        text = item.cemeteryLocation
    }
}


@BindingAdapter("setGraveFirstName")
fun TextView.setGraveFirst(item: Grave?){
    item?.firstName?.let {
        text = item.firstName
    }
}

@BindingAdapter("setGraveLast")
fun TextView.setGraveLast(item: Grave?){
    item?.lastName?.let {
        text = item.lastName
    }

    Log.i("BindingAdapter", "setGrave last name called")
}

@BindingAdapter("setGraveBirth")
fun TextView.setGraveBirth(item: Grave?){
    item?.birthDate?.let {
        text = item.birthDate
    }
}

@BindingAdapter("setGraveDeath")
fun TextView.setGraveDeath(item: Grave?){
    item?.deathDate?.let {
        text = item.deathDate
    }
}

@BindingAdapter("setGraveMarried")
fun TextView.setGraveMarried(item: Grave?){
    item?.marriageYear?.let {
        text = item.marriageYear
    }
}

@BindingAdapter("setGraveComment")
fun TextView.setGraveComment(item: Grave?){
    item?.comment?.let {
        text = item.comment
    }
}

@BindingAdapter("setGraveNum")
fun TextView.setGraveNum(item: Grave?){
    item?.graveNumber?.let {
        text = item.graveNumber
    }
}




@BindingAdapter("setEtGraveFirstName")
fun EditText.setGraveFirst(item: Grave?){
    item?.firstName?.let {
        setText(item.firstName)
    }
}

@BindingAdapter("setEtGraveLast")
fun EditText.setGraveLast(item: Grave?){
    item?.lastName?.let {
        setText(item.lastName)
    }

}

@BindingAdapter("setEtGraveBirth")
fun EditText.setGraveBirth(item: Grave?){
    item?.birthDate?.let {
        setText(item.birthDate)
    }
}

@BindingAdapter("setEtGraveDeath")
fun EditText.setGraveDeath(item: Grave?){
    item?.deathDate?.let {
        setText(item.deathDate)
    }
}

@BindingAdapter("setEtGraveMarried")
fun EditText.setGraveMarried(item: Grave?){
    item?.marriageYear?.let {
        setText(item.marriageYear)
    }
}

@BindingAdapter("setEtGraveComment")
fun EditText.setGraveComment(item: Grave?){
    item?.comment?.let {
        setText(item.comment)
    }
}

@BindingAdapter("setEtGraveNum")
fun EditText.setGraveNum(item: Grave?){
    item?.graveNumber?.let {
        setText(item.graveNumber)
    }
}

