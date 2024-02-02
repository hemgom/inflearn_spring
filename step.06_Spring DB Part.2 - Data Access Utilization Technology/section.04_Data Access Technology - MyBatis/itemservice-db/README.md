# 섹션 04. 데이터 접근 기술 - MyBatis
## 01. MyBatis 소개
`MyBatis`는 `SQL Mapper`이며 `JdbcTemplate`보다 많은 기능을 제공한다.<br/>
가장 매력적인 점은 SQL 을 XML 에 편리하게 작성할 수 있고 `동적 쿼리를 매우 편리하게 작성`할 수 있다는 점이다.  
<br/>

### SQL 이 여러줄에 걸쳐 작성 될 때 비교
#### JdbcTemplate
```
String sql = "update item " +
"set item_name=:itemName, price=:price, quantity=:quantity " +
"where id=:id";
```
- 문자열을 합침에 유의하여 띄어쓰기를 신경쓰는 등 신경써야 할 부분이 많음
#### MyBatis
```
<update id="update">
    update item
    set item_name=#{itemName},
        price=#{price},
        quantity=#{quantity}
    where id = #{id}
</update>
```
- `XML`에 작성하므로 `SQL`이 길어져도 문자를 더하는데 불편함이 없음  
<br/>

### 상품 검색 로직 - 동적 쿼리 비교
#### JdbcTemplate
```
String sql = "select id, item_name, price, quantity from item";
//동적 쿼리
if (StringUtils.hasText(itemName) || maxPrice != null) {
    sql += " where";
}

boolean andFlag = false;
if (StringUtils.hasText(itemName)) {
    sql += " item_name like concat('%',:itemName,'%')";
    andFlag = true;
}

if (maxPrice != null) {
    if (andFlag) {
        sql += " and";
    }
    sql += " price <= :maxPrice";
}

log.info("sql={}", sql);
return template.query(sql, param, itemRowMapper());
```
- 자바 코드로 직접 동적 쿼리를 작성해야 함
#### MyBatis
```
<select id="findAll" resultType="Item">
    select id, item_name, price, quantity
    from item
    <where>
        <if test="itemName != null and itemName != ''">
            and item_name like concat('%',#{itemName},'%')
        </if>
        <if test="maxPrice != null">
            and price &lt;= #{maxPrice}
        </if>
    </where>
</select>
```
- 동적 쿼리를 매우 편리하게 작성할 수 있는 다양한 기능을 제공함  
<br/>

### 설정의 장단점
`JdbcTemplate`은 스프링 내장 기술이므로 별다른 설정 없이 사용할 수 있으며, `MyBatis`는 설정이 필요함  
<br/>

### 정리
프로젝트에 `동적 쿼리` 또는 `복잡한 쿼리`가 많다면 `MyBatis`를 사용, `단순 쿼리`가 많다면 `JdbcTemplate`을 선택하면 됨  
<br/><br/><br/>

## 06. MyBatis 기능 정리1 - 동적 쿼리
### MyBatis 공식 메뉴얼
- `MyBatis 공식` 메뉴얼: https://mybatis.org/mybatis-3/ko/index.html
- `MyBatis 스프링 공식` 메뉴얼: https://mybatis.org/spring/ko/index.html  
<br/>

### 동적 SQL
MyBatis 가 제공하는 최고의 기능이자 이유, 제공되는 기능은 아래와 같다.
- `if`
- `choose (when, otherwise)`
- `trim (where, set)`
- `foreach`  
<br/>

#### if
```
<select id="findActiveBlogWithTitleLike" resultType="Blog">
    SELECT * FROM BLOG
    WHERE state = ‘ACTIVE’
    <if test="title != null">
        AND title like #{title}
    </if>
</select>
```
- 조건에 따라 값을 추가할지 말지 판단  
<br/>

#### choose, when, otherwise
```
<select id="findActiveBlogLike" resultType="Blog">
    SELECT * FROM BLOG WHERE state = ‘ACTIVE’
    <choose>
        <when test="title != null">
            AND title like #{title}
        </when>
        <when test="author != null and author.name != null">
            AND author_name like #{author.name}
        </when>
        <otherwise>
            AND featured = 1
        </otherwise>
    </choose>
</select>
```
- 자바의 switch 구문과 유사한 구문도 사용 가능  
<br/>

#### trim, where, set
```
<select id="findActiveBlogLike" resultType="Blog">
    SELECT * FROM BLOG
    <where>
        <if test="state != null">
            state = #{state}
        </if>
        <if test="title != null">
            AND title like #{title}
        </if>
        <if test="author != null and author.name != null">
            AND author_name like #{author.name}
        </if>
    </where>
</select>
```
<br/>

#### foreach
```
<select id="selectPostIn" resultType="domain.blog.Post">
    SELECT *
    FROM POST P
    <where>
        <foreach item="item" index="index" collection="list"
            open="ID in (" separator="," close=")" nullable="true">
            #{item}
        </foreach>
    </where>
</select>
```
- 컬렉션을 반복 처리할 때 사용  
<br/><br/><br/>

## 07. MyBatis 기능 정리2 - 기타 기능
### 애노테이션으로 SQL 작성
```
@Select("select id, item_name, price, quantity from item where id=#{id}")
Optional<Item> findById(Long id);
```
- 위와 같이 XML 대신에 애노테이션으로 SQL 을 작성할 수 있음
- `@Insert`, `@Update`, `@Delete`, `@Select` 기능이 제공됨
- `XML`에서 `<select id ="findById"> ~ </select>`는 제거해야 함
- 단점은 `동적 SQL`이 해결되지 않으므로 간단한 경우에만 사용함  
<br/>

### 문자열 대체
`#{}` 문법은 `?`를 넣고 파라미터를 바인딩하는 `PreparedStatement`를 사용함
- 문자 그대로를 처리하고 싶은 경우 `${}` 문법을 사용함
- `주의`: 인젝션 공격을 당할 수 있으므로 가급적 사용해선 안 됨  
<br/>

### 재사용 가능한 SQL 조각
```
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```
- `<sql>`을 사용하면 SQL 코드를 재사용 할 수 있음
```
<select id="selectUsers" resultType="map">
    select
        <include refid="userColumns"><property name="alias" value="t1"/></include>,
        <include refid="userColumns"><property name="alias" value="t2"/></include>
    from some_table t1
        cross join some_table t2
</select>
```
- `<include>`를 통해 `<sql>`조각을 찾아 사용할 수 있음
```
<sql id="sometable">
    ${prefix}Table
</sql>

<sql id="someinclude">
    from
        <include refid="${include_target}"/>
</sql>

<select id="select" resultType="map">
    select
        field1, field2, field3
    <include refid="someinclude">
        <property name="prefix" value="Some"/>
        <property name="include_target" value="sometable"/>
    </include>
</select>
```
- 프로퍼티 값을 전달할 수도 있으며 해당 값은 내부에서 사용 가능함  
<br/>

### Result Maps
결과 매핑시 테이블은 `user_id`지만 객체는 `id`이다.<br/>
컬럼명과 객체의 프로퍼티 명이 다른데, 이럴 경우 `별칭(as)`을 사용하면 된다.
```
<select id="selectUsers" resultType="User">
    select
        user_id as "id",
        user_name as "userName",
        hashed_password as "hashedPassword"
    from some_table
    where id = #{id}
</select>
```
<br/>

#### 별칭 설정없는 문제해결 방법
```
<resultMap id="userResultMap" type="User">
    <id property="id" column="user_id" />
    <result property="username" column="user_name"/>
    <result property="password" column="hashed_password"/>
</resultMap>

<select id="selectUsers" resultMap="userResultMap">
    select user_id, user_name, hashed_password
    from some_table
    where id = #{id}
</select>
```
- `resultMap`을 선언해 사용하면 됨