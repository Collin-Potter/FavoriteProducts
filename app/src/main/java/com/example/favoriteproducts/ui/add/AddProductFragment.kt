package com.example.favoriteproducts.ui.add

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.favoriteproducts.R
import com.example.favoriteproducts.data.model.Product
import kotlinx.android.synthetic.main.fragment_add_product.*

class AddProductFragment : Fragment() {

    private lateinit var viewModel: AddProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(AddProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_add_product, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_add -> {
                saveProductInfo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveProductInfo() {
        val product = Product(
            textInputName.editText?.text.toString()
        )
        viewModel.addProduct(product)

        Navigation.findNavController(view!!).navigateUp()
    }
}