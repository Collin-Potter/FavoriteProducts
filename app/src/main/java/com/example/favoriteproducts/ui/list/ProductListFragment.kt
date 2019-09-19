package com.example.favoriteproducts.ui.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.favoriteproducts.R
import com.example.favoriteproducts.data.model.Product
import com.example.favoriteproducts.ui.list.util.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_products_list.*

class ProductListFragment : Fragment(),
    ProductListAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener{

    private lateinit var searchView: SearchView
    private lateinit var viewModel: ProductListViewModel

    private lateinit var tempProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_product_list,menu)

        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductList().observe(this, Observer<List<Product>> { products ->
            products?.let {
                populateProductList(products)
            }
        })

        setSwipeHandler()

        addFab.setOnClickListener {
            view.findNavController().navigate(R.id.action_productListFragment_to_addProductFragment)
        }
    }

    override fun onQueryTextChange(newText: String?) = true

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchProducts(query!!)
        return true
    }

    override fun onClose(): Boolean {
        viewModel.getAllProducts()
        searchView.onActionViewCollapsed()
        return true
    }

    private fun populateProductList(productList: List<Product>) {
        productRecyclerView.adapter = ProductListAdapter(productList, this)
    }

    private fun setSwipeHandler(){
        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val product: Product = viewModel.getProductList().value!![viewHolder.adapterPosition]
                tempProduct = product
                viewModel.removeProduct(product)
                viewModel.getAllProducts()
                showUndoSnackbar(product.name)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(productRecyclerView)
    }

    private fun showUndoSnackbar(name: String){
        val snackbar: Snackbar = Snackbar.make(view!!, "Item deleted: ${name}", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo", {
            undoDelete()
        }).show()
    }

    private fun undoDelete() {
        viewModel.addProduct(tempProduct)
    }

    override fun onItemClick(product: Product, itemView: View) {
        val productBundle = Bundle().apply {
            putInt("PRODUCT_ID", product.id)
        }
        view?.findNavController()
            ?.navigate(R.id.action_productListFragment_to_productDetailsFragment, productBundle)
    }
}