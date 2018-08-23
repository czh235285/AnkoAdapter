## JsonAdapter
[![](https://jitpack.io/v/czh235285/JsonAdapter.svg)](https://jitpack.io/#czh235285/JsonAdapter)



简单页面adapter封装，直接传入JsonArray。
减少Bean类文件，如果遇到经常修改字段的后台，用这个就不用到处改了。
复杂页面，经常修改数据的时候，JsonArray不方便修改，就不要使用这个了。

* 适用kotlin开发者
* 适用kotlin开发者
* 适用kotlin开发者
* 重要事情说三遍，只是用于自己开发，kotlin可以直接用xml中的id，所以ViewHolder我也没继续封装。。。懒癌晚期，java使用就需要自己findViewById，有点麻烦咯。
* 可以直接传入JsonArray的adapter精简封装，占用体积小
* 简单页面，不想写bean类的时候，直接传入JsonArray。
* 减少Bean类文件，如果遇到经常修改字段的后台，用这个就不用到处改了。
* 复杂页面，经常修改数据的时候，JsonArray不方便修改，就不要使用这个了。

use Gradle:

```
repositories {
  maven { url "https://jitpack.io" }
  mavenCentral()
  google()
}
dependencies {

  // 只是要JsonAdapter只需要这个版本就行了。
  implementation 'com.github.czh235285:JsonAdapter:1.0.3'
  
  // 最新1.1.1加入了anko的adapter，用不上DSL布局就不要导入这个。
  implementation 'com.github.czh235285:JsonAdapter:1.1.1'
}
```
## 用法介绍

使用代码 :

```

class TestAdapter(mLayoutResId: Int, mData: JSONArray?) : JsonAdapter(mLayoutResId, mData) {
    override fun convert(holder: BaseViewHolder, item: JSONObject?) {
        holder.itemView.run {
            item?.optString("name")?.let { tvName.text = it }
        }
    }
}

```

支持空布局、添加头部、尾部:

```

   adapter.setEmptyView(layoutInflater.inflate(R.layout.empty,null))
   adapter.addHeaderView(layoutInflater.inflate(R.layout.head, null))
   adapter.addFooterView(layoutInflater.inflate(R.layout.foot, null))

```

item点击/长按事件:

```

    adapter.setOnItemClickListener { view, position, item ->
            Toast.makeText(this@MainActivity, "点击了position==$position,item=${item.optString("name")}", Toast.LENGTH_LONG).show()
        }
        
	adapter.setOnItemLongClickListener { view, position, item ->
            Toast.makeText(this@MainActivity, "长按了position==$position,item=${item.optString("name")}", Toast.LENGTH_LONG).show()
        }

```

刷新数据/加载更多 :


```

    adapter.replaceData(JSONArray)
    adapter.addData(JSONArray)

```
