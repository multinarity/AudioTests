using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class SoundCheck : MonoBehaviour
{

    public AudioClip audio;
    public AudioSource audioSource;
    public Image backgroundColor;
    // Start is called before the first frame update
    void Start()
    {
       
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void PlaySound()
    {
        backgroundColor.color = Color.white;
        audioSource.PlayOneShot(audio);
        StartCoroutine(Reset());
    }

    public IEnumerator Reset()
    {
        yield return new WaitForSeconds(5f);
        backgroundColor.color = Color.black;

    }
}
