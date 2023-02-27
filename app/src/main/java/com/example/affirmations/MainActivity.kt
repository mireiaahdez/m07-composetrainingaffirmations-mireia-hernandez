/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.data.Datasource
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationApp()
        }
    }
}

@Composable
fun AffirmationApp() {
    // TODO 4. Apply Theme and affirmation list
    AffirmationsTheme {
        val count = remember {
            mutableStateOf(0)
        }
        Column() {
            TextButton(onClick = { count.value +=1 }) {
                Text(text = "Me han pulsado  ${count.value}")
            }
            AffirmationList(affirmationList = Datasource().loadAffirmations())
        }



    }

}

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    //TODO 3. Wrap affirmation card in a Lazy column
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        items(affirmationList) { affirm ->
            AffirmationCard(affirmation = affirm)
        }
    }
}

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    // TODO 1. Your card UI

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        border = BorderStroke(2.dp, Color.Red),
        shape = RoundedCornerShape(16.dp),

    ) {
    Column(modifier = Modifier.animateContentSize(
        animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
        ))) {

        Row() {

            Image(
                painter = painterResource(id = affirmation.imageResourceId),
                contentDescription = affirmation.stringResourceId.toString()
            )

            Text(
                text = stringResource(id = affirmation.stringResourceId),
                Modifier
                    .padding(start = 10.dp)
                    .weight(0.5f),

                )

            CardItemButton(
                expanded = expanded,
                onClick = { expanded = !expanded })
        }

       if (expanded){
           CardDescription(affirmation.description)
       }
    }



    }


}

@Preview
@Composable
private fun AffirmationCardPreview() {
    // TODO 2. Preview your card

    AffirmationCard(affirmation = Datasource().loadAffirmations().last())


}

@Composable
private fun CardItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(onClick = onClick) {
        Icon(
            imageVector = if(expanded) Icons.Filled.ArrowForward else Icons.Filled.ArrowDropDown ,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null
        )
    }
}

@Composable
fun CardDescription(@StringRes cardDescription: Int, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.h5,
        )
        Text(
            text = stringResource(cardDescription),
            style = MaterialTheme.typography.body1,
        )
    }
}
