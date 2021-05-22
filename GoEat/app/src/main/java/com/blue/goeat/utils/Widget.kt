package com.blue.goeat.utils

import android.content.Context
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blue.goeat.R

interface TileContent {
    fun getTileText(): String
}

class WidgetArgs(
    val widgetName: String,
    val context: Context,
    val sceneRoot: ViewGroup,
    val title: String = "Title",
    val defaultValue: String = "Select",
    val callback: (TileContent) -> Unit
)

class Widget(private val widgetArgs: WidgetArgs) {
    private var scene1: Scene
    private var scene2: Scene
    private var titleValue: TextView
    private var transitionManager: TransitionManager
    private lateinit var widgetAdapter: WidgetAdapter
    fun setTileContentList(tileContentList: List<TileContent>) {
        println("data changed")
        widgetAdapter.tileContentList = tileContentList
    }

    init {

        widgetArgs.context.apply {
            titleValue = widgetArgs.sceneRoot.findViewById(R.id.titleValue)
            widgetArgs.sceneRoot.findViewById<TextView>(R.id.title).text = widgetArgs.title
            titleValue.text = widgetArgs.defaultValue

            scene1 = Scene(
                widgetArgs.sceneRoot,
                widgetArgs.sceneRoot.findViewById(R.id.container) as View
            )
            scene2 = Scene.getSceneForLayout(widgetArgs.sceneRoot, R.layout.scene2, this)

            transitionManager = TransitionInflater.from(this)
                .inflateTransitionManager(
                    R.transition.scene3_transition_manager,
                    widgetArgs.sceneRoot
                )

            widgetAdapter = WidgetAdapter(widgetArgs.widgetName) {
                println("Going to scene 1")
                widgetArgs.callback(it)
                transitionManager.transitionTo(scene1)
                widgetArgs.sceneRoot.findViewById<TextView>(R.id.titleValue).text =
                    it.getTileText()
            }

            titleValue.setOnClickListener {
                println("Going to scene 2")
                goToScene2(widgetArgs)
            }
            goToScene2(widgetArgs)
        }

    }

    fun setDefaultTitle() {
        widgetArgs.sceneRoot.findViewById<TextView>(R.id.titleValue)?.text = widgetArgs.defaultValue
    }


    private fun Context.goToScene2(widgetArgs: WidgetArgs) {
        transitionManager.transitionTo(scene2)
        widgetArgs.sceneRoot.findViewById<TextView>(R.id.title).text = widgetArgs.title
        val widgetList = widgetArgs.sceneRoot.findViewById<RecyclerView>(R.id.widgetList)
        widgetList.adapter = widgetAdapter
        widgetList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        widgetAdapter.notifyDataSetChanged()
    }

    class WidgetTile(
        private val cardTile: CardView,
        private val context: Context,
        private val callback: (TileContent) -> Unit
    ) {

        private var isSelected = false
        private val txtTile = cardTile.findViewById<TextView>(R.id.txtTile)
        private lateinit var widgetValue:TileContent

        init {
            cardTile.setOnClickListener {
                cardTile.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        if (isSelected) R.color.white else R.color.gold
                    )
                )
                isSelected = !isSelected
                println("Clicked!!!")
                callback(widgetValue)
            }
        }

        fun setTileText(tileTxt: String) {
            txtTile.text = tileTxt
        }

        fun bind(widgetValue: TileContent) {
            this.widgetValue = widgetValue
        }

    }

    class WidgetAdapter(
        private val widgetName: String,
        private val callback: (TileContent) -> Unit
    ) : RecyclerView.Adapter<BaseWidgetViewHolder>() {
        enum class ViewType{
            EmptyView,
            TileView
        }
        val emptyTileContent = object : TileContent {
            override fun getTileText(): String {
                return widgetName
            }
        }
        var tileContentList: List<TileContent> = ArrayList()
            set(value) {
                println("data changed")
                field = value
                notifyDataSetChanged()
            }

        init {
            println("Size of tile content list is ${tileContentList.size}")
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWidgetViewHolder {
            println("WidgetAdapter onCreateViewHolder $this")
            return when {
                ViewType.EmptyView.ordinal == viewType -> EmptyWidgetViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.empty_widget_tile,
                        parent,
                        false
                    )
                )
                else ->
                    WidgetViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.widget_tile,
                            parent,
                            false
                        ), callback
                    )

            }
        }

        override fun getItemCount(): Int {
            println("WidgetAdapter getItemCount ${tileContentList.size}")
            return if(tileContentList.isEmpty())1 else tileContentList.size
        }

        override fun getItemViewType(position: Int): Int {
            return if(tileContentList.isEmpty())ViewType.EmptyView.ordinal else ViewType.TileView.ordinal
        }

        override fun onBindViewHolder(holder: BaseWidgetViewHolder, position: Int) {
            println("WidgetAdapter onBindViewHolder $this")
            holder.apply {
                when {
                    tileContentList.isEmpty() -> bind(position, emptyTileContent)
                    else -> bind(position, tileContentList[position])
                }
            }
        }
    }

    abstract class BaseWidgetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int, widgetValue: TileContent)
    }

    class EmptyWidgetViewHolder(view: View) : BaseWidgetViewHolder(view) {
        private val cardTileText: TextView = view.findViewById<CardView>(R.id.cardTile)
            .findViewById(R.id.txtTile)

        override fun bind(position: Int, widgetValue: TileContent) {
            cardTileText.text = "${widgetValue.getTileText()} are not available"
        }
    }

    class WidgetViewHolder(view: View, callback: (TileContent) -> Unit) : BaseWidgetViewHolder(view) {
        private val widget = WidgetTile(view.findViewById(R.id.cardTile), view.context, callback)

        init {
            println("WidgetAdapter WidgetViewHolder $this")

        }

        override fun bind(position: Int, widgetValue: TileContent) {
            widget.bind(widgetValue)
            widget.setTileText(widgetValue.getTileText())
        }

    }
}