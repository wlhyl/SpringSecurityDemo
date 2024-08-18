import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecureDigestAlgorithm
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

/**
 * 过期时间(单位:秒)
 */
const val ACCESS_EXPIRE: Int = 60 * 60 * 24

/**
 * 加密算法
 */
private val ALGORITHM: SecureDigestAlgorithm<SecretKey, SecretKey> = Jwts.SIG.HS256

/**
 * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
 * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
 * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
 */
private const val SECRET = "secretKey,secretKey,secretKey,secretKey,"

/**
 * 秘钥实例
 */
val KEY: SecretKey = Keys.hmacShaKeyFor(SECRET.toByteArray())

/**
 * jwt签发者
 */
private const val JWT_ISS = "Demo"

/**
 * jwt主题
 */
private const val SUBJECT = "Token"

/*
 * 这些是一组预定义的声明，它们 不是强制性的，而是推荐的 ，以 提供一组有用的、可互操作的声明 。
 * iss: jwt签发者
 * sub: jwt所面向的用户
 * aud: 接收jwt的一方
 * exp: jwt的过期时间，这个过期时间必须要大于签发时间
 * nbf: 定义在什么时间之前，该jwt都是不可用的.
 * iat: jwt的签发时间
 * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
 */
fun genAccessToken(userId: UInt): String {
    // 令牌id
    val uuid = UUID.randomUUID().toString()
    val exprireDate: Date = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE.toLong()))

    return Jwts.builder() // 设置头部信息header
        .header()
        .add("typ", "JWT")
        .add("alg", "HS256")
        .and() // 设置自定义负载信息payload
        .claim("user_id", userId.toString()) // 令牌ID
        .id(uuid) // 过期日期
        .expiration(exprireDate) // 签发时间
        .issuedAt(Date()) // 主题
        .subject(SUBJECT) // 签发者
        .issuer(JWT_ISS) // 签名
        .signWith<SecretKey>(KEY, ALGORITHM)
        .compact()
}


/**
 * 解析token
 *
 * @param token token
 * @return Jws<Claims>
</Claims> */
fun parseClaim(token: String): Jws<Claims> {
    return Jwts.parser()
        .verifyWith(KEY)
        .build()
        .parseSignedClaims(token)
}

fun parseHeader(token: String): JwsHeader {
    return parseClaim(token).header
}

fun parsePayload(token: String): Claims {
    return parseClaim(token).payload
}
