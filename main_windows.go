//go:build windows

package main

import (
	"fmt"
	"io"
	"os"
	"strings"

	"github.com/cjxpj/nebula/appfiles"
	"github.com/cjxpj/nebula/dic"
	"github.com/cjxpj/nebula/juice"
)

func main() {
	if len(os.Args) != 2 {
		go func() {
			juice.Run()
		}()
		select {}
	}

	cmdInput := os.Args[1]

	if cmdInput == "-v" {
		fmt.Print(appfiles.Version)
		return
	}

	file, err := os.Open(cmdInput)
	if err != nil {
		fmt.Print(err)
		return
	}
	defer file.Close()

	var result strings.Builder
	buf := make([]byte, 1024)
	for {
		n, err := file.Read(buf)
		if n > 0 {
			result.Write(buf[:n])
		}
		if err != nil {
			if err == io.EOF {
				break
			}
			fmt.Print(err)
			return
		}
	}

	results := dic.NewDic(result.String(), "Main", "system").Run()
	fmt.Print(results)
}
