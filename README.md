# hello-bottom-navigation

BottomNavigationViewとJetpack Navigationを組み合わせた画面遷移を実装してみる。

## トラブル解消

#### タブAのサブページを表示している状態でタブBに遷移し、タブAに戻ってもタブAが選択状態にならない事象を解消する

事象  
https://github.com/seabat/hello-bottom-navigation/assets/4818667/1d14739d-abf8-4d46-b333-628c5172e284

対策  
`setOnItemSelectedListener` を override し、true
を返す。([BottomNavigationViewとJetpack Navigationを組み合わせた画面遷移の実装の勘所](https://inside.luchegroup.com/entry/2023/05/08/113236)
を参考)

```diff
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bottomNav
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        setupWithNavController(bottomNav, fragment.navController)
+       // NOTE: setOnItemSelectedListener の上書きは BottomNavigationView#setupWithNavController
+       //       の後に実行する。
+       bottomNav.setOnItemSelectedListener { item ->
+           NavigationUI.onNavDestinationSelected(item, fragment.navController)
+           true
+       }
    }
```

解決  
https://github.com/seabat/hello-bottom-navigation/assets/4818667/9c280b53-d353-4cf1-b923-ccf754bed149

