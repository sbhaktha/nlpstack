package org.allenai.repr.sentence

import org.allenai.aitk.postag._

trait PostagsSupertrait extends TokensSupertrait {
  this: Sentence =>

  type token <: PostaggedToken

  def postags: Seq[String] = tokens.map(_.postag)
}

trait Postags extends PostagsSupertrait {
  this: Sentence =>

  type token = PostaggedToken
}

trait Postagger extends Postags {
  this: Sentence =>
  def tokenizer: org.allenai.aitk.tokenize.Tokenizer
  def postagger: org.allenai.aitk.postag.Postagger

  override lazy val tokens: Seq[PostaggedToken] =
    postagger.postag(tokenizer)(this.text)
}

