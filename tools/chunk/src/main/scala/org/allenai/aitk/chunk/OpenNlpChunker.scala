package org.allenai.aitk
package chunk

import org.allenai.common.Resource
import org.allenai.aitk.tokenize._
import org.allenai.aitk.postag._

import opennlp.tools.chunker._

import java.net.URL

class OpenNlpChunker(
  val model: ChunkerModel)
    extends Chunker {
  def this() = this(OpenNlpChunker.loadDefaultModel())

  val chunker = new ChunkerME(model)

  def chunkPostagged(tokens: Seq[postag.PostaggedToken]): Seq[ChunkedToken] = {
    val chunks = chunker.chunk(tokens.map(_.string).toArray, tokens.map(_.postag).toArray)
    (tokens zip chunks) map { case (token, chunk) => ChunkedToken(token, chunk) }
  }
}

object OpenNlpChunker {
  private def defaultModelName = "en-chunker.bin"
  val defaultModelUrl: URL = {
    val url = this.getClass.getClassLoader.getResource(defaultModelName)
    require(url != null, "Could not load default chunker model: " + defaultModelName)
    url
  }

  def loadDefaultModel(): ChunkerModel = loadModel(defaultModelUrl)

  private def loadModel(url: URL): ChunkerModel = {
    Resource.using(url.openStream()) { stream =>
      new ChunkerModel(stream)
    }
  }
}

object OpenNlpChunkerMain extends ChunkerMain {
  override lazy val tokenizer = new SimpleEnglishTokenizer()
  override lazy val postagger = new OpenNlpPostagger()
  override lazy val chunker = new OpenNlpChunker()
}
