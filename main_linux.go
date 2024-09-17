//go:build linux

package main

import "github.com/cjxpj/nebula/juice"

func main() {
	juice.Run()
	select {}
}
