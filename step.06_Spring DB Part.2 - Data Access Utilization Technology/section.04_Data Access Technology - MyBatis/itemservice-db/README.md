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