package tm.gg
/**
  * @author Terence Munro <terry@zenkey.com.au>
  */
package object io {
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try { f(resource) } finally { resource.close() }
}
