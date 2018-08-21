# Quick Review AppStructure
ใช้ `MVVM + Dagger` ตาม [`Architecture Component`](https://developer.android.com/topic/libraries/architecture/index.html) ของ Android เขียนด้วย `kotlin`


* layer View : `Fragment Activity` ต่างๆ view จะไม่ทำหน้าที่คำนวณ business logic ใดๆ มีหน้าที่แสดงผลอย่างเดียวเท่านั้น<br>
* layer ViewModel : ดึงข้อมูลต่างๆจากฝั่ง Repository มาและ business logic ต่างๆจะถูกทำที่นี้ และส่งไปให้ view แสดงผล<br>
* layer Repository : `Database Network` 

# View
 `Activity` ให้ extend [`AppStructureActivity`] มาจะทำการ overried method ที่ต้องใช้มาให้
 ```
 class MainActicity : AppStructureActivity(){
 ...
 }
 ```
 
 `Fragment` ให้ extend [`AppStructureFragment`] มาจะทำการ overried method ที่ต้องใช้มาให้
 
```
class MainFragment : AppStructureFragment(){
...
}
```
### method setupLayoutView return xml layout ในนั้น
```
override fun setupLayoutView(): Int {
        return R.layout.activity_main
    }
```
### setup มี method bindViewModel ในนั้นแล้วไม่ต้องทำท่า ViewModelProviders.if(context,viewModelFactory).get(class)
```
private lateinit var mainViewModel : MainViewModel
...
override fun setup(){
    mainViewModel = bindViewModel(MainViewModel::class.java)
}
```
* เปิด Activity ใหม่ให้ใช้ `openActivity(class,bundle)` 
* Toast ให้ใช้ toast(...) ได้เลยไม่ต้องพิมเองยาวๆ `toast("Hello")`
* เพิ่ม Fragment `addFragment(supportFragmentManager,layout,fragment)`
Fragment มีทั้ง `replaceFragment`,`removeFragment`<br>
* ใส่ null safety `?` หลัง view ด้วยทุกครั้ง App จะได้ไม่บึ้ม💥💥<br>
```
btnLogin?.setOnClickListener{}
```
ไม่ต้อง findViewById อีกต่อไปแล้วใน kotlin ***เวลาตั้งชื่อตัวแปรใน xml ใช้เป็น sneck_case แล้วมาเปลี่ยน ชื่อตัวแปรเอาใน class ให้เป็น camelCase***
```
import kotlinx.android.synthetic.main.login_activity.btn_login as btnLogin
```
### Dagger
เวลามีการสร้าง Activity ใหม่ให้ไป register Activity ตัวนั้นใน [`ActivityModule.kt`] ด้วยแอปจะได้ไม่บึ้ม
```
...
@ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeNewActivity(): NewActivity
...
```
ถ้าเป็น Fragment ก็ให้ไป register ใน [`FragmentModule.kt`] เช่นกัน
```
...
@ContributesAndroidInjector
    abstract fun contributeAppointmentFragment(): LoginFragment
...
```
ViewModel ด้วยให้ไป register ไว้ด้วยใน ViewModelModule
```
    @Binds
    @IntoMap
    @ViewModelKey(NewViewModel::class)
    abstract fun bindMainViewModel(newViewModel: NewViewModel): ViewModel
```

# ViewModel
* ใช้ `LiveData` ในการทำให้ View กับ ViewModel คุยกันได้
ถ้า ViewModel มีการเชื่อมต่อฝั่ง  Repository ให้ทำการ `@Inject constructor(repository : Repository)`เข้ามาด้วยบน class
```
class LoginViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() 
```
จากที่ประกาศ ViewModel ไว้ใน View แล้วให้สามารถใช้ได้เลย ❗️อย่าลืม bindViewModel(ViewModel::class) ก่อนใน ฝั่ง View
[`MainAvtivity.kt`]
```
override fun setup() {
        loginViewModel = bindViewModel(LoginViewModel::class.java)

        //observer ค่าที่เปลี่ยนไปของตัวแปร
        loginViewModel.user.observe(this, Observer { user->
            //do something
        })
        // เรียกใช้ function ใน ViewModel เพราะฝั่ง View จะไม่ทำ business logic
        loginViewModel.setText("text")
    }
```
[`MainViewModel.kt`]
```
val user: LiveData<Resource<User>> = Transformations
            .switchMap(_login) { login ->
                if (login == null) {
                    AbsentLiveData.create()
                } else {
                    userRepository.loadUser(login)
                }
            }
            
 fun setText(text: String?) {
       //do something

    }           
```
# Repository
เป็นฝั่งที่ทำเกี่ยวกับการยิง service หรือ ดึงข้อมูลมาจาก Database (Room Library) 
ฝั่ง Repository จะประกอบไปด้วย
* NetworkBoundResource
* Dao
* LiveDataCallAdpaterFactory
* LiveDAtaCallAdater
* ApiService (retrofit)

## data class ปกติ Java จะต้องทำเป็น getter setter kotlin cool กว่าไม่ต้องทำแบบนั้นให้ประกาศเป็น data class ได้เลย
[`User.kt`]
```
@Entity(primaryKeys = ["id"])
data class User(
@SerializedName("id")
@ColumnInfo(name = "id")
val id : String,
@SerializedName("name")
val username : String
)
```
❗️ใส่ `@SerializedName` ทุกครั้งเวลาประกาศชื่อตัวแปรใน data class กัน proguard เปลี่ยนชื่อ feild ❗️
`@ColumnInfo(name = "...")` เป็นการประกาศหัวตาราง Database ว่าจะให้ feild นี้ชื่ออะไรเวลา Query จะได้ดึงมาใช้ได้ถูกต้อง
## Dao เป็น class ของ Database มีการ Query Database แบบปกติที่เคยทำกัน เป็น Room ที่เข้ามาจัดการเรื่องของการฝัง code sql ลงไปในตัว class หลัก จึงแยกออกมาทำเป็น Dao แทน 
[`UserDao.kt`]
```
@Dao
abstract class UserDao {
    @Query("SELECT * FROM user ")
    abstract fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    abstract fun findByLogin(id: String): LiveData<User>
}

```
## ถ้ามีการเพิ่ม data class ใหม่ให้ไปเพิ่มในตัว [`DatabaeManager.kt`] ด้วยใน Array ของ entities 
```
@Database(entities = 
[
  User::class,
  NewDataClass::class
],version = 1,
exportSchema = false
)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun userDao(): UserDao
    
    abstact fun newDataClassDao() : NewDataClassDao
}

```
## หลังจากนั้นไป register data class ตัวใหม่ใน [`DatabaseModule.kt`] ด้วย
```
...
@Singleton
    @Provides
    fun provideNewDataClassDao(db: DatabaseManager): NewDataClassDao {
        return db.newDataClassDao()
    }
 ...

```
# Unit Test
Comming..
# Code style
* ไม่ใช้ `Log` แล้วในการดู console ให้ใช้ `Timber` แทน
❗️* ตั้งชื่อตัวแปรให้สื่อต่อการใช้งานด้วยทุกครั้ง❗️
❗️* extract String resouce ด้วยทุกครั้งจะได้รองรับ 2 ภาษาหรือมากกว่า❗️
❗️* ไม่กำหนดค่า width heigth หรืออื่นๆลงไปดื้อๆ ให้ทำเป็น dimen แทน รองรับการทำงานบนหน้าจอหลายขนาด❗️

# Other
* custom colour ของ Logcat ด้วยเพื่อชีวิตที่ง่ายขึ้น
```
  Assert #A6A6A6
  Debug #2081E4
  Error #FF6A6B
  Info #119B26
  Verbose #C5C1Bd
  Warning #DFAE00
```
  ไม่ใต้องใช้ตามก็ได้ใส่ไว้เผื่อคิดสีกันไม่ออก
  
* เบื่อ progress bar แบบเดิมๆแนะนำให้ใช้ plugin Nyan Progress Bar 😸
* ไม่ต้องนั่ง Import รูปเข้า ทุกขนาดของ value resource ใช้ plugin Android Drawable Importer (very good)







