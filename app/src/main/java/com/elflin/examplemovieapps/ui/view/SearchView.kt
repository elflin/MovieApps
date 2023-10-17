package com.elflin.examplemovieapps.ui.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchView(){
    Text(text = "Search View")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchPreview(){
    SearchView()
}