package com.example.calc.ui.composeables

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calc.CalcViewModel

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: CalcViewModel
) {
    // Fetching the local context for using the Toast
    val mContext = LocalContext.current

    val isInvalidOperation = viewModel.showToast.observeAsState()

    // Display the toast if the toastMessage changes
    if (isInvalidOperation.value!!) {
        Toast.makeText(
            mContext,
            viewModel.toastMessage,
            Toast.LENGTH_SHORT
        ).show()
        viewModel.setShowToast(false)
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        DisplayUnit(
            modifier = Modifier.weight(1f),
            arguments = viewModel.arguments.args.observeAsState()
        )
        Spacer(modifier = Modifier.padding(16.dp))
        ButtonRows(
            modifier = Modifier.weight(2f),
            viewModel = viewModel
        )
    }
}
