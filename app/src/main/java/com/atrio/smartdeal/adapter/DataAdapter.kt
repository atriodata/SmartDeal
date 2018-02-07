package `in`.android2.com.autocompletetextview_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.atrio.smartdeal.ProductData

import java.util.ArrayList

/**
 * Created by IM021 on 3/31/2017.
 */

internal class DataAdapter(private val mContext: Context, private val originalList: List<ProductData>) : BaseAdapter(), Filterable {
    private val suggestions = ArrayList<ProductData>()
    private val filter = CustomFilter()

    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getItem(i: Int): ProductData {
        return suggestions[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.activity_list_item, viewGroup, false)
            holder = ViewHolder()
            holder.autoText = view!!.findViewById(android.R.id.text1) as TextView
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.autoText!!.setText(suggestions[i].product_title)
        return view

    }

    private class ViewHolder {
        internal var autoText: TextView? = null
    }

    override fun getFilter(): Filter {
        return filter
    }

    private inner class CustomFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            suggestions.clear()
            // Check if the Original List and Constraint aren't null.
            if (originalList != null && constraint != null) {
                for (i in originalList.indices) {
                    // Compare item in original list if it contains constraints.
                    if (originalList[i].product_title.toLowerCase().contains(constraint)) {
                        // If TRUE add item in Suggestions.
                        suggestions.add(originalList[i])
                    }
                }
            }
            // Create new Filter Results and return this to publishResults;
            val results = Filter.FilterResults()
            results.values = suggestions
            results.count = suggestions.size

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}