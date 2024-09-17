//go:build so

package main

//#include <jni.h>
import "C"
import "github.com/cjxpj/nebula/juice"

// Java_com_cjxpj_juice_rn_11_RunJuice
// Java_com_cjxpj_juice_MainActivity_RunJuice

//export Java_com_cjxpj_juice_MainActivity_RunJuice
func Java_com_cjxpj_juice_MainActivity_RunJuice(env *C.JNIEnv, obj C.jobject) {
	juice.Run()
}

func main() {}
